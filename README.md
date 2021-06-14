# EcoLanda
## Un nuevo software de gestión de Huertos

El nuevo producto de la empresa _Mubisoft Eco_, llamado _EcoLanda_, está diseñado para implementar una mejora en la gestión de huertos y facilitar la producción y venta de producto local y ecológico. El objetivo de esta aplicación es facilitar el consumo de producto local y ayudar a los pequeños agricultores a obtener beneficios de los productos que cultivan. Gracias a la aplicación de gestión integrada en el ordenador del agricultor y el punto de venta online, los agricultores tienen la oportunidad de abrir su mercado a un mayor número de clientes. Además, el huerto será monitorizado mediante sensores de temperatura y humedad, para facilitar la recolección de datos sobre el ambiente de crecimiento y así optimizar la producción de este.

![EcoLanda](https://1.bp.blogspot.com/-7ko10zkRFDE/YLj9L5cPRFI/AAAAAAAABKg/_wtqHqM-OXIufrvirjNzeU33vAujpYIWwCLcBGAsYHQ/s16000/eco_landa.png)

El conjunto de productos del sistema de _EcoLanda_ esta formado por dos aplicaciones desarrolladas en Java y una aplicación diseñada para la placa **Olimex STM32F40507GTx**. Para el correcto funcionamiento del sistema, se recomienda que estas aplicaciones estén funcionando en conjunto.

---
## Montaje del Sistema

A continuación, se va a explicar como montar el sistema para el correcto funcionamiento.

### Sistema de la Placa

Para realizar las mediciones, hay que primero montar el sensor de temperatura y la bascula.

- ### Sensor de Temperatura:
    - Conectar el pin de alimentación al pin de 3.3V de la izquierda.
    - Conectar el pin de tierra a tierra.
    - Conectar el pin restante a PB0.

- ### Bascula:
    - Conectar el pin de alimentación de HX711 a 3.3V de la derecha.
    - Conectar el pin de tierra (GND) a tierra.
    - Conectar DOUT (DT) a PF8.
    - Conectar SCK PF9

- ### Cable Serial
    - Conectar el cable serial al puerto de _USART6_.
    - _USART6_ será el puerto serial en la esquina de la placa.

### Servidor Conectado a la Placa:

Esta aplicación tiene que estar conectada a la placa mediante puerto serial. Una vez este conectada arrancar la aplicación y se conectará via serial automaticamente. Una vez arrancada se activara el servidor de sockets para que los clientes se conecten.

### Aplicación Cliente

Para que la aplicación cliente funcione correctamente la base de datos debe de estar en marcha. Hay un archivo de configuración en la carpeta Files de la aplicación donde se puede especificar la IP del servidor con la base de datos y el servidor.

En el caso de querer conectarse a si mismo como servidor de la base de datos o de sockets, la IP en el archivo de configuración debe de ser **127.0.0.1** o **localhost**.