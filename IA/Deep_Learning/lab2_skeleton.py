import tensorflow as tf
import tensorflow.keras
from tensorflow.keras.utils import to_categorical
import keras
import numpy as np
from keras.datasets import mnist
from matplotlib import pyplot as plt
(X_train, y_train), (X_test, y_test) = mnist.load_data()

X_train = X_train.astype('float32')
X_test = X_test.astype('float32')
X_train  = X_train / 255
X_test  = X_test / 255
num_pixels = X_train.shape[1] * X_train.shape[2]

y_new = np.zeros(y_train.shape)
y_new[np.where(y_train==5.0)[0]] = 1
y_train = y_new

y_new = np.zeros(y_test.shape)
y_new[np.where(y_test==5.0)[0]] = 1
y_test = y_new

y_train = to_categorical(y_train)
y_test = to_categorical(y_test)

#To complete

from keras.models import Sequential
from keras.layers import Dense, Flatten

model = Sequential()
model.add(Flatten(input_shape=(28,28)))
model.add(Dense(10,input_dim=num_pixels, activation='softmax', kernel_initializer='normal',))
model.add(Dense(2, activation='softmax', kernel_initializer='normal'))
model.compile(loss='categorical_crossentropy', optimizer='sgd', metrics=['accuracy'])

model.summary()

epochs = 100
batch_size = 128
model_list = model.fit(X_train, y_train,validation_data =( X_test,y_test), epochs=epochs, batch_size=batch_size)
print(model_list.history.keys())
plt.plot(range(epochs+1), model_list.history['val_accuracy'])
plt.plot(range(epochs+1), model_list.history['accuracy'])