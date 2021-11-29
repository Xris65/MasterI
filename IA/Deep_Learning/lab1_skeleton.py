import numpy as np

#In this first part, we just prepare our data (mnist) 
#for training and testing

import tensorflow.keras
from tensorflow.keras.datasets import mnist
(X_train, y_train), (X_test, y_test) = mnist.load_data()
num_pixels = X_train.shape[1] * X_train.shape[2]
X_train = X_train.reshape(X_train.shape[0], num_pixels).T
X_test = X_test.reshape(X_test.shape[0], num_pixels).T
y_train = y_train.reshape(y_train.shape[0], 1)
y_test = y_test.reshape(y_test.shape[0], 1)
X_train = X_train.astype('float32')
X_test = X_test.astype('float32')
y_train = y_train.astype('float32')
y_test = y_test.astype('float32')
X_train  = X_train / 255
X_test  = X_test / 255


#We want to have a binary classification: digit 5 is classified 1 and 
#all the other digits are classified 0

y_new = np.zeros(y_train.shape)
y_new[np.where(y_train==5.0)[0]] = 1
y_train = y_new

y_new = np.zeros(y_test.shape)
y_new[np.where(y_test==5.0)[0]] = 1
y_test = y_new


y_train = y_train.T
y_test = y_test.T


m = X_train.shape[1] #number of examples

#Now, we shuffle the training set
np.random.seed(138)
shuffle_index = np.random.permutation(m)
X_train, y_train = X_train[:,shuffle_index], y_train[:,shuffle_index]


#Display one image and corresponding label 
import matplotlib
import matplotlib.pyplot as plt
def printImg():
    i = 9
    print('y[{}]={}'.format(i, y_train[:,i]))
    plt.imshow(X_train[:,i].reshape(28,28), cmap = matplotlib.cm.binary)
    plt.axis("off")
    plt.show()

#printImg()
#Let start our work: creating a neural network
#First, we just use a single neuron.
#Q1
def sigmoid(z):
    return 1 / (1 + np.exp(-z))

#Q2
#ŷ = sigma(somme de 1 à n de (w_i * x_i + b)

def ŷ(X, w, b):
    return sigmoid(np.dot(w, X) + b)
#Q3
#L(y,ŷ) = -(y * ln(ŷ) + (1-y) * ln(1-ŷ))
def loss(y, ŷ):
    m = y.shape[1]
    return format((-(y * np.log(ŷ) + (1-y) * np.log(1-ŷ)).sum() / m), '.5f')

learning_rate = 1.
X = X_train 
y = y_train
print('Y.shape={}'.format(y.shape))
n_x = X.shape[0]
W = np.random.randn(1, n_x) * 0.01
b = np.zeros((1, 1))

def Question3():
    for epoch in range(100):
        z = np.matmul(W, X) + b
        ŷ = sigmoid(z)
        L = loss(y, ŷ)
        dW = (1./m) * np.matmul((ŷ - y), X.T)
        db = (1./m) * np.sum(ŷ - y,axis = 1, keepdims = True)
        W = W - learning_rate * dW
        b = b - learning_rate * db
        if (epoch%20 == 0):
            print('epoch = {}, loss = {}'.format(epoch, L))
    print('final loss: ',L)

#Question3()
#Q4

def Question4():
    ŷ = sigmoid(np.matmul(W, X_test) + b)
    y_pred = np.zeros(len(ŷ[0]))
    y_pred[np.where(ŷ[0]>0.5)[0]] = 1
    acc = 0
    for i in range(len(y_pred)):
        if y_pred[i] == y_test[0,i]:
            acc += 1
    acc/y_pred.shape[0]

Question4()

#Q2.1
#Ecrire l’expression de la dérivée de L par rapport à w_i pour tout i.
#dL/dw_i = dL/dŷ * dŷ/z * dz/w_i
#dL/dŷ = -y/ŷ + (1-y)/(1-ŷ)
#dŷ/dz = ŷ(1-ŷ)
#dz/dw_i = x_i

#dL/dw_i = (-y/ŷ + (1-y)/(1-ŷ)) * (ŷ(1-ŷ)) * x_i
#dL/dw_i = (-y + ŷ) * x_i


