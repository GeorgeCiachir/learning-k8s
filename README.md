# The app just greets the caller, by responding with a html page
# There are 3 ways of deploying the app

1. locally, from the IDE 
   * run the application with "local" Spring profile 
   * in this case there are some properties that need to be specified in the application.properties file
   * the applicationSettingsLocation and applicationSecretsLocation are required to simulate the volumes generated in K8s for the ConfigMap and Secret
     * defaultHtmlLocation=path/to/the/initial/html
     * htmlLocation=path/to/the/html-volume
     * applicationSettingsLocation=path/to/the/settings-volume
     * applicationSecretsLocation=path/to/the/secrets-volume
     * appDBPassword=parola_applicatiei -> there is no actual DB this is just to simulate a secret being read from the Secret object in K8s
    
2. locally, in Docker
   * to start in Docker, run this command:
     * `docker run -it -v /path/to/your/html/volume/on/disk:/app-volume -v /path/to/your/app-settings/volume/on/disk:/app-settings -v /path/to/your/app-secrets/volume/on/disk:/app-secrets --env-file .\docker_env_file -p 8080:8080 georgeciachir/small-app:3.0` 
   * there is a file in the project, `docker_env_file` from which the environment variables are passed to the Docker container, and these have the same values as the ones in the K8s deployment file
     * APP_SETTINGS_HTML_LOCATION=/app-volume
     * APP_SETTINGS_SETTINGS_LOCATION=/app-settings
     * APP_SETTINGS_SECRETS_LOCATION=/app-secrets
     * APP_SETTINGS_DEFAULT_HTML_LOCATION=/application/templates
     * SECRET_APP_DB_PASSWORD=parola_aplicatiei

3. in K8s
   * for the openshift deployment there is a separate file: `os-cloud-app-deployment.yml` due to some platform specific file adjustments
