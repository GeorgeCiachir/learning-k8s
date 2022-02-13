
### Get the configmaps:
```kubectl get cm name -o yaml```


### Several ways to create a config map (see creating_config_map.png ):

1. from yml file: ```kubectl apply -f configmap-app-settings.yml```

2. loading data from a config file, such as the settings.config in this folder:
   i. using `--from-env-file`:
      * in this case, the config is loaded as key/value pairs and each value can be accessed via the key
      * this is essentially the same as applying a yml file using kubectl
      * `kubectl create cm app-settings --from-env-file=settings.config`
    
   ii. using `--from-file`: 
      * in this case, the file name becomes the key and the actual properties in the file are stored as a blob of text
      * to access the properties, the text must be parsed and individually identify the values
      *  `kubectl create cm app-settings --from-file=settings.config`
   
   iii. using `--from-literal`:
     * essentially, loading key-value-pairs manually:
     * `kubectl create cm app-settings --from-literal=key1=value1 --from-literal=key2=value2`

### Option 1 and 2.i. from above will create a config map that is accessible in a key/value style

### However, there is the option of creating a volume for the ConfigMap, by assigning a volume (see additional explanation in deployment.yml) 