# chess-refactor

Refactorización de Chess Master.

La refactorización se analiza en términos de la estructura del proyecto y la calidad del código fuente.

## Herramientas
 - Netbeans
 - Sonarqube
 - Maven

## Situación inicial

### Estructura del proyecto
Esta estructura muestra una agrupación de todas las clases en un solo paquete, lo cual deja abierta la posibilidad de implementar patrones para refactorizar la fuente.

![image](https://user-images.githubusercontent.com/51771490/186519485-5972a2d8-a992-4448-8765-b886a55bc74c.png)

### Análisis del código
Se utilizó Sonarqube para analizar el estado del codigo fuente inicial y obtener métricas para comparar los resultados de la refactorización

![image](https://user-images.githubusercontent.com/51771490/186518769-e28c0ab0-5846-402c-a9ab-5997c7b0d555.png)

Mayormente presenta code smells relacionados a malas prácticas, código duplicado, malas implementaciones y falta de agrupamiento en paquetes.

## Resultados

### Estructura del proyecto

La arquitectura del proyecto fue cambiada a una más comprensible y estructurada, agrupada según conveniencia comprendiendo el contexto de la aplicación.

![image](https://user-images.githubusercontent.com/51771490/186520519-664bf188-bbd4-4048-abd5-8feacf1d0c10.png)

### Análisis del código

Los resultados del análisis del código fuente refactorizado muestran mejoras en la implementación y mayor limpieza.

![image](https://user-images.githubusercontent.com/51771490/186518149-723c15ea-6538-463e-a73e-be84a2c570a5.png)
