import keras
import matplotlib.patches as patches
from keras.layers import Dense, Dropout, Conv2D, MaxPooling2D, Flatten
from keras.models import Sequential
from keras import utils as np_utils
import matplotlib.pyplot as plt
# %matplotlib inline
import numpy as np

# On some implementations of matplotlib, you may need to change this value
IMAGE_SIZE = 72


def generate_a_drawing(figsize, U, V, noise=0.0):
    fig = plt.figure(figsize=(figsize, figsize))
    ax = plt.subplot(111)
    plt.axis('Off')
    ax.set_xlim(0, figsize)
    ax.set_ylim(0, figsize)
    ax.fill(U, V, "k")
    fig.canvas.draw()
    imdata = np.frombuffer(fig.canvas.tostring_rgb(), dtype=np.uint8)[
        ::3].astype(np.float32)
    imdata = imdata + noise * np.random.random(imdata.size)
    plt.close(fig)
    return imdata


def generate_a_rectangle(noise=0.0, free_location=False):
    figsize = 1.0
    U = np.zeros(4)
    V = np.zeros(4)
    if free_location:
        corners = np.random.random(4)
        top = max(corners[0], corners[1])
        bottom = min(corners[0], corners[1])
        left = min(corners[2], corners[3])
        right = max(corners[2], corners[3])
    else:
        side = (0.3 + 0.7 * np.random.random()) * figsize
        top = figsize/2 + side/2
        bottom = figsize/2 - side/2
        left = bottom
        right = top
    U[0] = U[1] = top
    U[2] = U[3] = bottom
    V[0] = V[3] = left
    V[1] = V[2] = right
    return generate_a_drawing(figsize, U, V, noise)


def generate_a_disk(noise=0.0, free_location=False):
    figsize = 1.0
    if free_location:
        center = np.random.random(2)
    else:
        center = (figsize/2, figsize/2)
    radius = (0.3 + 0.7 * np.random.random()) * figsize/2
    N = 50
    U = np.zeros(N)
    V = np.zeros(N)
    i = 0
    for t in np.linspace(0, 2*np.pi, N):
        U[i] = center[0] + np.cos(t) * radius
        V[i] = center[1] + np.sin(t) * radius
        i = i + 1
    return generate_a_drawing(figsize, U, V, noise)


def generate_a_triangle(noise=0.0, free_location=False):
    figsize = 1.0
    if free_location:
        U = np.random.random(3)
        V = np.random.random(3)
    else:
        size = (0.3 + 0.7 * np.random.random())*figsize/2
        middle = figsize/2
        U = (middle, middle+size, middle-size)
        V = (middle+size, middle-size, middle-size)
    imdata = generate_a_drawing(figsize, U, V, noise)
    return [imdata, [U[0], V[0], U[1], V[1], U[2], V[2]]]


im = generate_a_rectangle(10, True)
plt.imshow(im.reshape(IMAGE_SIZE, IMAGE_SIZE), cmap='gray')

im = generate_a_disk(10)
plt.imshow(im.reshape(IMAGE_SIZE, IMAGE_SIZE), cmap='gray')

[im, v] = generate_a_triangle(20, False)
plt.imshow(im.reshape(IMAGE_SIZE, IMAGE_SIZE), cmap='gray')


def generate_dataset_classification(nb_samples, noise=0.0, free_location=False):
    # Getting im_size:
    im_size = generate_a_rectangle().shape[0]
    X = np.zeros([nb_samples, im_size])
    Y = np.zeros(nb_samples)
    print('Creating data:')
    for i in range(nb_samples):
        if i % 10 == 0:
            print(i)
        category = np.random.randint(3)
        if category == 0:
            X[i] = generate_a_rectangle(noise, free_location)
        elif category == 1:
            X[i] = generate_a_disk(noise, free_location)
        else:
            [X[i], V] = generate_a_triangle(noise, free_location)
        Y[i] = category
    X = (X + noise) / (255 + 2 * noise)
    return [X, Y]


def generate_test_set_classification():
    np.random.seed(42)
    [X_test, Y_test] = generate_dataset_classification(300, 20, True)
    Y_test = keras.utils.np_utils.to_categorical(Y_test, 3)
    return [X_test, Y_test]


def generate_dataset_regression(nb_samples, noise=0.0):
    # Getting im_size:
    im_size = generate_a_triangle()[0].shape[0]
    X = np.zeros([nb_samples, im_size])
    Y = np.zeros([nb_samples, 6])
    print('Creating data:')
    for i in range(nb_samples):
        if i % 10 == 0:
            print(i)
        [X[i], Y[i]] = generate_a_triangle(noise, True)
    X = (X + noise) / (255 + 2 * noise)
    return [X, Y]


def visualize_prediction(x, y):
    fig, ax = plt.subplots(figsize=(5, 5))
    I = x.reshape((IMAGE_SIZE, IMAGE_SIZE))
    ax.imshow(I, extent=[-0.15, 1.15, -0.15, 1.15], cmap='gray')
    ax.set_xlim([0, 1])
    ax.set_ylim([0, 1])

    xy = y.reshape(3, 2)
    tri = patches.Polygon(xy, closed=True, fill=False,
                          edgecolor='r', linewidth=5, alpha=0.5)
    ax.add_patch(tri)

    plt.show()


def generate_test_set_regression():
    np.random.seed(42)
    [X_test, Y_test] = generate_dataset_regression(300, 20)
    return [X_test, Y_test]


[X_train, y_train] = generate_dataset_classification(300, 20)
[X_test, y_test] = generate_test_set_classification()
y_train = keras.utils.np_utils.to_categorical(y_train)

X_train = X_train.astype('float32')
y_train = y_train.astype('float32')
X_test = X_test.astype('float32')
y_test = y_test.astype('float32')



epochs = 20
batch_size = 100
# Simple neuronal network
model = Sequential()
num_pixels = IMAGE_SIZE * IMAGE_SIZE
model.add(Flatten(input_shape=(num_pixels,)))
model.add(Dense(num_pixels, activation='softmax', kernel_initializer='normal',))
model.add(Dense(num_pixels, activation='relu', kernel_initializer='normal',))
model.add(Dense(num_pixels, activation='sigmoid', kernel_initializer='normal',))
model.add(
    Dense(y_train.shape[1], activation='sigmoid', kernel_initializer='normal'))
model.compile(loss='categorical_crossentropy',
              optimizer='adam', metrics=['accuracy'])
model.summary()
model_list = model.fit(X_train, y_train,validation_data=(X_test, y_test), epochs=epochs,
                       batch_size=batch_size, verbose=0)
print(model_list.history.keys())
plt.plot(range(epochs), model_list.history['val_accuracy'])
plt.plot(range(epochs), model_list.history['accuracy'])

score = model.evaluate(X_test,y_test)
print("Simple neural network accuracy : %.2f%%" %  (score[1]*100))

# Model CNN
X_train = X_train.reshape(X_train.shape[0], IMAGE_SIZE, IMAGE_SIZE, 1)
X_test = X_test.reshape(X_test.shape[0], IMAGE_SIZE, IMAGE_SIZE, 1)
print(X_train.shape)
CNNmodel = Sequential()
CNNmodel.add(Conv2D(32,
                 kernel_size=(3, 3),
                 activation='softmax',
                 input_shape=(IMAGE_SIZE, IMAGE_SIZE, 1)))
CNNmodel.add(Conv2D(64,
                 kernel_size=(3, 3),
                 activation='softmax'))
CNNmodel.add(MaxPooling2D(pool_size=(2, 2)))
CNNmodel.add(Flatten())
CNNmodel.add(Dense(num_pixels, activation='softmax'))
CNNmodel.add(Dense(num_pixels, activation='relu'))
CNNmodel.add(Dense(num_pixels, activation='sigmoid'))
CNNmodel.add(Dense(y_train.shape[1], activation='softmax'))
CNNmodel.add(Dropout(0.5))
CNNmodel.compile(loss='categorical_crossentropy',
              optimizer='adam', metrics=['accuracy'])
CNNmodel_list = CNNmodel.fit(X_train, y_train, validation_data=(X_test, y_test) ,  epochs=epochs,
                      batch_size=batch_size, verbose=0)
print(CNNmodel_list.history.keys())
plt.plot(range(epochs), CNNmodel_list.history['val_accuracy'])
plt.plot(range(epochs), CNNmodel_list.history['accuracy']) 
score = CNNmodel.evaluate(X_test,y_test)
print("CNN neural network accuracy : %.2f%%" %  (score[1]*100))

