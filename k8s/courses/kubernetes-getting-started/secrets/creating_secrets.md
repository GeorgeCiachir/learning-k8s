# Are very similar to ConfigMaps

# It is not recommended creating a Secret from a deployment file
## because the secrets themselves are only Base64 encoded and if they are stored in a repo, they are exposed

# Regardless of it was created, a cluster admin should restrict access to secrets to only some users
## because, again, the secrets are Base64 encoded, meaning any cluster user can easily decode it if it can access it 

* create a secret from literal: 
  * `kubectl create secret generic my-secret --from-literal=pwd=my-password`

* create a secret from file
  * `kubectl create secret generic my-secret --from-file=ssh-privatekey=~/.ssh/id_rsa --from-file=ssh-publickey=~/.ssh/id_rsa.pub`

* create a secret from a key pair
  * `kubectl create secret tls tls-secret --sert=path/to/tls.cert --key=path/to/tls.key`


### Just like ConfigMaps, a volume can be used to store secrets