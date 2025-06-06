#!/bin/bash

# Create storage directory
sudo mkdir -p /mnt/data/mysql
sudo chmod 777 /mnt/data/mysql

# Pre-pull images to avoid long pull times
sudo ctr images pull docker.io/kawtar2/product-service:latest
sudo ctr images pull docker.io/kawtar2/order-service:latest
sudo ctr images pull docker.io/kawtar2/payment-service:latest
sudo ctr images pull docker.io/library/mysql:8