# Taller 4: Programación Orientada a Objetos

## Descripcion del Proyecto
El sistema permite administrar una colección organizada de cartas del juego Pokémon TCG. Gracias a su arquitectura orientada a objetos, el software centraliza el inventario permitiendo modificar atributos específicos, listar la colección de manera dinámica y calcular la puntuación de poder en tiempo real mediante fórmulas personalizadas por tipo de carta.

La aplicación cuenta con una Interfaz Gráfica de Usuario (GUI) interactiva dividida en dos secciones principales: la **Pestaña de Administración**, encargada de las operaciones CRUD con impacto inmediato y persistente en el archivo físico (`Sobres.txt`), y la **Pestaña de Visualización**, diseñada para inspeccionar la colección mediante algoritmos de ordenamiento intercambiables (Nombre, Rareza y Poder) junto con un visor ampliado que despliega de forma dinámica la información detallada e imágenes de las cartas seleccionadas.

## Integrantes
- Carlos Alberto Montenegro Pérez - 22.154.893-0 - Akr0yy
- Daniel Alexanders Robles Valdenegro - 20.738.244-2 - DaniRV10

## Estructura del proyecto

El código se encuentra organizado bajo una arquitectura limpia y en capas, desacoplando la interfaz gráfica de la lógica del negocio mediante interfaces y aplicando de forma estricta patrones de diseño:

* **`dominio`**: Contiene las entidades del modelo que representan los elementos coleccionables.
  * **Carta.java**: Clase abstracta base que modela las propiedades comunes de una carta (Nombre, Rareza) y define los contratos polimórficos de serialización y aceptación de visitantes.
  * **PokemonCarta.java**: Subclase que extiende de Carta, incorporando los atributos específicos de *daño* y *cantidad de energías*.
  * **ItemCarta.java**: Subclase que extiende de Carta, incorporando el atributo específico de *bonificación*.
  * **SuportCarta.java**: Subclase que extiende de Carta, incorporando el atributo específico de *efectos por turno*.
  * **EnergiaCarta.java**: Subclase que extiende de Carta, incorporando el atributo específico de *elemento*.
  * **CartaVisitor.java / CalculadorPoderVisitor.java**: Interfaces e implementaciones del patrón **Visitor** utilizadas para externalizar y calcular matemáticamente el poder individual de cada carta sin corromper el modelo.

* **`factory`**: Encapsula la creación de los objetos del sistema.
   * **CartaFactory.java**: Clase encargada de la instanciación encapsulada de las subclases de cartas a partir del procesamiento y parseo de cadenas de texto extraídas del archivo.

* **`strategy`**: Aloja la familia de algoritmos intercambiables para la ordenación de la lista.
  * **Ordenamiento.java**: Interfaz base que define el contrato común para los algoritmos de ordenamiento.
  * **OrdenarNombre.java**: Estrategia de ordenación alfabética por el nombre de la carta.
  * **OrdenarPoder.java**: Estrategia de ordenación descendente basada en el poder calculado.
  * **OrdenarRareza.java**: Estrategia de ordenación de mayor a menor según la rareza.

* **`gui`**: Capa de presentación interactiva construida mediante la librería Java Swing bajo un enfoque modular y desacoplado:
  * **VentanaPrincipal.java**: Ventana raíz de la aplicación que organiza el espacio de trabajo mediante un contenedor de pestañas (`JTabbedPane`), coordinando la sincronización de datos entre la vista de administración y el catálogo visual.
  * **PanelAdministracion.java**: Panel encargado del mantenimiento CRUD en caliente. Implementa validaciones en tiempo de ejecución, formularios interactivos y el bloqueo automático de campos base (Nombre y Rareza) para cumplir estrictamente con las reglas de modificación del taller.
  * **PanelColeccion.java**: Catálogo visual estructurado en una cuadrícula envolvente bidimensional (`HORIZONTAL_WRAP`). Integra el componente interactivo de ordenamiento dinámico por patrones de estrategia y renderiza las cartas de forma personalizada.
  * **VistaAmpliadaCarta.java**: Diálogo emergente de carácter modal (`APPLICATION_MODAL`) que se dispara al interactuar con el catálogo. Se encarga de unificar la presentación de estadísticas base, atributos dinámicos y el cálculo exacto de poder resuelto a través del patrón **Visitor**.
  * **CartaTipoUtil.java**: Clase de utilidad lógica y no instanciable que encapsula el uso seguro de `instanceof` para mapear las jerarquías de dominio con la interfaz gráfica, proveyendo etiquetas y arreglos de datos formateados para edición o lectura.
  * **ImagenUtil.java**: Motor utilitario encargado del procesamiento de recursos gráficos en el disco. Resuelve de forma segura el enlace físico de imágenes probando extensiones dinámicas (`.png`, `.jpg`, `.jpeg`, `.gif`) y mitiga excepciones visuales mediante una imagen por defecto (`default.png`).

* **`logica`**: Contiene los contratos, los algoritmos de negocio.
  * **ISistema.java**: Interfaz que define el contrato de comportamiento del sistema (gestión CRUD, lectura/escritura y ordenación).
  * **SistemaImpl.java**: Clase controladora maestra que implementa `ISistema` bajo el patrón **Singleton**. Administra las colecciones en memoria (`ArrayList`), resguarda la persistencia física del archivo y delega las estrategias de sort.
  * **App.java**: Clase que contiene el método `main`, encargado de inicializar el sistema y lanzar de forma segura la GUI.

* **`Archivos Externos`**:
  * **Sobres.txt**: Archivo plano que actúa como la base de datos persistente, almacenando los registros con delimitadores punto y coma (`;`).
  * **images/**: Directorio que almacena los recursos visuales indexados por el nombre exacto de cada carta, incluyendo una imagen por defecto para mitigar excepciones de carga.


## Patrones de Diseño Implementados
1. **Singleton**: Garantiza que `SistemaImpl` posea una única instancia global con acceso coordinado a la base de datos en memoria.
2. **Factory Method**: Desacopla la lógica de creación de objetos en `CartaFactory`, aislando el lector de archivos de los detalles constructivos de cada tipo de carta.
3. **Visitor**: Centraliza el cálculo matemático de las puntuaciones de poder en un agente externo (`CalcularPoderVisitor`), eliminando la necesidad de saturar las clases de dominio con lógica matemática.
4. **Strategy**: Permite intercambiar dinámicamente los criterios de ordenación de la lista en la interfaz visual sin alterar la estructura del contenedor de datos.

## Instrucciones de ejecución.

Para ejecutar el programa se debe tener instalado un entorno como **Eclipse** o **Java JDK 21**.

En caso de realizar compilación por Consola (Terminal/PowerShell):
```bash
javac -d bin src/dominio/*.java src/factory/*.java src/strategy/*.java src/logica/*.java src/gui/*.java
```
Luego se ejecuta con el siguiente codigo.

```bash
java -cp bin logica.App
```
Tambien para que el software funcione correctamente, asegurar que en la carpeta txts encuentren los archivos .txts que se indican en la estructura del proyecto.