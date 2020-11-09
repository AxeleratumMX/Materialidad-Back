# Proyecto AmericanTower eContract

## Descripcion Modulos

Modulo at-contract-core, contiene las clases comnunes a todas las apis a desarrollar.
Modulo at-contract-notification-api, contiene las apis utilizadas para modulo Notificaciones.

## Empaquetamiento

Para el empaquetamiento de cualquiera de las apis, lo primero que tenemos que hacer es un mvn install, parados en la raiz del proyecto:

```sh
$ mvn install
```

Empaquetamiento de las APIs, a modo de ejemplo, api americantower-contract-notification-api

```sh
$ cd americantower-contract-notification-api
$ mvn clean install
```

## Ejecucion / Debug
Desde un IDE, agregar la siguiente VM option 

```sh
-DSPRING_PROFILE=local
```

En ambiente de pruebas, ejecutar el script que se encuentra en la raiz de cada módulo 

```sh
./start.sh
```

Cada script start.sh ejecuta el siguiente comando

```sh
nohup java -jar -Xms256m -Xmx512m -Xss256k -DSPRING_PROFILE=test target/americantower-contract-swagger-0.0.1-SNAPSHOT.jar > documentation-api.log &
```





