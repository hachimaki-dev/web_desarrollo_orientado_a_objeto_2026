# Tutorial de JavaFX: de un archivo JSON a una biblioteca con portadas reales

**Asignatura:** Desarrollo Orientado a Objetos  
**Tecnologías:** Java, JavaFX, Maven y Gson  
**Proyecto:** BiblioApp  
**Nivel:** Inicial, paso a paso

## 1. ¿Qué vamos a construir?

Desarrollaremos una aplicación de escritorio para consultar usuarios y visualizar los materiales de una biblioteca.

Al terminar, la aplicación permitirá:

- Cargar libros y revistas desde un archivo JSON.
- Representar los materiales mediante clases Java y herencia.
- Diferenciar libros de revistas según sus atributos.
- Separar los datos, la lógica y la interfaz mediante MVC.
- Mostrar los materiales en una `TableView`.
- Cargar portadas reales en lugar de mostrar las direcciones de las imágenes como texto.
- Navegar desde la pantalla principal hasta la pantalla de materiales.

El objetivo no es solamente conseguir que funcione. En cada etapa aprenderemos por qué necesitamos cada clase y qué responsabilidad tiene.

## 2. Preparación del proyecto

Trabajaremos sobre un proyecto JavaFX con Maven.

Necesitamos:

- Un JDK compatible con la versión de JavaFX utilizada.
- Un proyecto JavaFX configurado con Maven.
- La biblioteca Gson para leer archivos JSON.
- Un editor de código, como Visual Studio Code.

Si el proyecto ya está configurado y la aplicación JavaFX se ejecuta, no es necesario crearlo nuevamente.

### Dependencia Gson

En el archivo `pom.xml`, dentro de la sección `dependencies`, debemos tener Gson:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.14.0</version>
</dependency>
```

No debemos reemplazar las dependencias JavaFX que ya utiliza el proyecto. Solo agregamos Gson si todavía no está presente.

**Prueba 1:** ejecuta la aplicación. Antes de continuar, debemos comprobar que JavaFX funciona correctamente.

## 3. Comprender la arquitectura MVC

Antes de programar, necesitamos distribuir las responsabilidades.

En nuestro proyecto utilizaremos tres paquetes principales:

```text
com.hachimakidev.biblioapp
├── model
├── controller
└── view
```

El paquete `model` contiene las clases que representan los datos y administran la información.

El paquete `controller` conecta la interfaz con el modelo y solicita las operaciones necesarias.

El paquete `view` construye las pantallas y los componentes visuales de JavaFX.

Por ejemplo, cuando el usuario presiona el botón «Ver materiales»:

1. La vista detecta el clic.
2. Se crea la pantalla de materiales.
3. El controlador obtiene los materiales del modelo.
4. La vista muestra los datos en una tabla.

No necesitamos que una clase encargada de representar un libro conozca cómo funciona un botón o cómo se dibuja una tabla.

## 4. Crear la clase base Material

Comenzaremos por representar el concepto más general: un material de biblioteca.

Dentro del paquete `model`, creamos `Material.java`.

```java
package com.hachimakidev.biblioapp.model;

public class Material {

    String titulo;
    String imagen;

    public Material(String titulo, String imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
```

### ¿Qué representa esta clase?

Tanto un libro como una revista tienen un título y una imagen de portada. Por eso, podemos colocar esos atributos en una clase común.

`Material` contiene dos atributos:

- `titulo`: nombre del material.
- `imagen`: dirección de la imagen que representa su portada.

El constructor permite crear un objeto inicializando ambos valores.

Los métodos `getTitulo()` y `getImagen()` permiten consultar los atributos. Los métodos `setTitulo()` y `setImagen()` permiten modificarlos.

### Actividad

Antes de continuar, responde:

1. ¿Por qué tiene sentido que un libro y una revista compartan la clase `Material`?
2. ¿Qué diferencia existe entre un atributo y un método?
3. ¿Qué valor devuelve `getImagen()`?

## 5. Crear la clase Libro mediante herencia

Ahora creamos `Libro.java` en el mismo paquete.

```java
package com.hachimakidev.biblioapp.model;

public class Libro extends Material {

    String autor;
    int paginas;

    public Libro(
            String titulo,
            String imagen,
            String autor,
            int paginas) {

        super(titulo, imagen);

        this.autor = autor;
        this.paginas = paginas;
    }

    public String getAutor() {
        return autor;
    }

    public int getPaginas() {
        return paginas;
    }
}
```

### ¿Qué significa `extends Material`?

Significa que `Libro` hereda de `Material`.

Un libro es un material, pero además posee características específicas, como su autor y su cantidad de páginas.

Por lo tanto, no necesitamos volver a declarar los atributos `titulo` e `imagen` en la clase `Libro`.

### ¿Para qué sirve `super()`?

La instrucción:

```java
super(titulo, imagen);
```

invoca el constructor de la clase padre `Material`.

De esta manera, la clase padre se encarga de inicializar los atributos que le corresponden.

Después, el constructor de `Libro` inicializa `autor` y `paginas`.

### Actividad

Crea un objeto `Libro` de prueba y consulta su título, autor y cantidad de páginas.

Comprueba que puedes obtener el título utilizando el método heredado `getTitulo()`.

## 6. Crear la clase Revista

Creamos `Revista.java`.

```java
package com.hachimakidev.biblioapp.model;

public class Revista extends Material {

    String editorial;
    String periodicidad;

    public Revista(
            String titulo,
            String imagen,
            String editorial,
            String periodicidad) {

        super(titulo, imagen);

        this.editorial = editorial;
        this.periodicidad = periodicidad;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }
}
```

Una revista también es un material, pero tiene información propia: la editorial y la periodicidad de publicación.

Observa que no repetimos el código de `titulo` e `imagen`. Ambos atributos pertenecen a la clase padre.

### Comprobación conceptual

La relación entre las clases queda así:

```text
             Material
             /      \
          Libro    Revista
```

`Libro` y `Revista` heredan de `Material`.

Esta relación es un ejemplo de herencia y también representa una relación de tipo «es un»: un libro es un material y una revista también es un material.

## 7. Preparar los datos en formato JSON

Ahora necesitamos una fuente de datos para nuestra biblioteca.

Dentro de `src/main/resources`, creamos el archivo `biblioteca.json`.

La estructura del archivo será un arreglo de objetos JSON.

```json
[
    {
        "tipo": "libro",
        "titulo": "El Hobbit",
        "autor": "J. R. R. Tolkien",
        "paginas": 282,
        "imagen": "https://servidor/portada-hobbit.jpg"
    },
    {
        "tipo": "revista",
        "titulo": "Hacker News",
        "editorial": "Ejemplo Editorial",
        "periodicidad": "Mensual",
        "imagen": "https://servidor/portada-revista.jpg"
    }
]
```

Las direcciones de las imágenes anteriores son ejemplos ilustrativos: debemos sustituirlas por direcciones reales que entreguen directamente los archivos de imagen.

Cada objeto contiene el atributo `tipo`. Este atributo nos permitirá identificar si debemos construir un `Libro` o una `Revista`.

Observa que los atributos no son idénticos:

- Los libros contienen `autor` y `paginas`.
- Las revistas contienen `editorial` y `periodicidad`.
- Ambos contienen `titulo` e `imagen`.

**Importante:** el JSON debe ser válido. Las comas, las comillas y los nombres de los atributos deben estar correctamente escritos.

## 8. Crear MaterialManager para leer el JSON

Dentro de `model`, creamos `MaterialManager.java`.

Esta clase será responsable de cargar el archivo y transformar sus datos en objetos Java.

```java
package com.hachimakidev.biblioapp.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MaterialManager {

    ArrayList<Material> material_arraylist = new ArrayList<>();

    public MaterialManager() {
        loadMaterial();
    }

    public void loadMaterial() {

        InputStream stream =
                getClass().getResourceAsStream("/biblioteca.json");

        if (stream == null) {
            throw new IllegalStateException(
                    "No se encontró biblioteca.json en resources");
        }

        try (InputStreamReader reader = new InputStreamReader(stream)) {

            Gson gson = new Gson();

            JsonObject[] materiales =
                    gson.fromJson(reader, JsonObject[].class);

            for (JsonObject material : materiales) {

                String tipo = material.get("tipo").getAsString();

                switch (tipo) {

                    case "libro":

                        String titulo =
                                material.get("titulo").getAsString();

                        String autor =
                                material.get("autor").getAsString();

                        int paginas =
                                material.get("paginas").getAsInt();

                        String imagen =
                                material.get("imagen").getAsString();

                        Libro libro =
                                new Libro(titulo, imagen, autor, paginas);

                        material_arraylist.add(libro);

                        break;

                    case "revista":

                        String tituloRevista =
                                material.get("titulo").getAsString();

                        String editorial =
                                material.get("editorial").getAsString();

                        String imagenRevista =
                                material.get("imagen").getAsString();

                        String periodicidad =
                                material.get("periodicidad").getAsString();

                        Revista revista = new Revista(
                                tituloRevista,
                                imagenRevista,
                                editorial,
                                periodicidad
                        );

                        material_arraylist.add(revista);

                        break;

                    default:
                        System.out.println(
                                "Tipo de material desconocido: " + tipo);
                        break;
                }
            }

        } catch (java.io.IOException e) {
            throw new IllegalStateException(
                    "No se pudo leer biblioteca.json", e);
        }
    }

    public ArrayList<Material> getMateriales() {
        return material_arraylist;
    }

    public void mostrarMateriales() {
        for (Material material : material_arraylist) {
            System.out.println(material.getTitulo());
        }
    }
}
```

### Comprender el proceso

Primero buscamos el archivo dentro de los recursos del proyecto:

```java
getClass().getResourceAsStream("/biblioteca.json");
```

El símbolo `/` indica que buscamos el recurso desde la raíz del classpath.

Después usamos `InputStreamReader` para leer el contenido del archivo y `Gson` para convertir el JSON en objetos que Java pueda procesar.

```java
JsonObject[] materiales =
        gson.fromJson(reader, JsonObject[].class);
```

En esta etapa todavía no tenemos objetos `Libro` ni `Revista`. Tenemos objetos `JsonObject` que representan las entradas del archivo.

Por eso recorremos el arreglo y consultamos el atributo `tipo`.

```java
String tipo = material.get("tipo").getAsString();
```

El `switch` decide qué clase debemos construir.

Si el tipo es `libro`, obtenemos sus atributos, creamos un `Libro` y lo agregamos al `ArrayList`.

Si el tipo es `revista`, hacemos lo mismo utilizando la clase `Revista`.

### ¿Por qué usamos `ArrayList<Material>`?

Porque queremos almacenar libros y revistas en una misma colección.

Como ambas clases heredan de `Material`, podemos guardar los dos tipos de objetos en un `ArrayList<Material>`.

Esto nos permite recorrer todos los materiales sin necesitar una lista separada para cada clase.

### Prueba 2

Agrega temporalmente esta línea después de crear un `MaterialManager`:

```java
materialManager.mostrarMateriales();
```

Ejecuta la aplicación y comprueba que aparecen los títulos en la consola.

Si no aparecen, revisa la ubicación del archivo, su contenido y los nombres de sus atributos.

## 9. Crear MaterialController

Ahora creamos `MaterialController.java` dentro del paquete `controller`.

```java
package com.hachimakidev.biblioapp.controller;

import java.util.ArrayList;

import com.hachimakidev.biblioapp.model.Material;
import com.hachimakidev.biblioapp.model.MaterialManager;

public class MaterialController {

    private MaterialManager materialManager;

    public MaterialController() {
        materialManager = new MaterialManager();
    }

    public ArrayList<Material> getMateriales() {
        return materialManager.getMateriales();
    }
}
```

### ¿Por qué necesitamos esta clase?

El controlador funciona como intermediario entre la vista y el modelo.

La vista no necesita conocer los detalles de lectura del JSON. Solo necesita solicitar los materiales.

El controlador se encarga de crear el administrador de materiales y ofrecer un método para obtener la colección.

Cuando construimos un `MaterialController`, se crea un `MaterialManager`, cuyo constructor carga el JSON.

La secuencia es:

```text
MaterialView
     |
     v
MaterialController
     |
     v
MaterialManager
     |
     v
biblioteca.json
```

La flecha representa una dependencia o una solicitud, no una copia automática de los datos.

## 10. Crear MaterialView y mostrar los materiales en una TableView

Llegamos a la interfaz gráfica.

Creamos `MaterialView.java` en el paquete `view`.

```java
package com.hachimakidev.biblioapp.view;

import com.hachimakidev.biblioapp.controller.MaterialController;
import com.hachimakidev.biblioapp.model.Material;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class MaterialView {

    private TableView<Material> tablaMateriales;
    private MaterialController materialController;

    public MaterialView() {
        materialController = new MaterialController();

        configurarTabla();
        cargarMateriales();
    }

    private void configurarTabla() {

        tablaMateriales = new TableView<>();

        TableColumn<Material, String> columnaTitulo =
                new TableColumn<>("Título");

        columnaTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo"));

        TableColumn<Material, String> columnaImagen =
                new TableColumn<>("Portada");

        columnaImagen.setCellValueFactory(
                new PropertyValueFactory<>("imagen"));

        tablaMateriales.getColumns().add(columnaTitulo);
        tablaMateriales.getColumns().add(columnaImagen);
    }

    private void cargarMateriales() {

        ObservableList<Material> materiales =
                FXCollections.observableArrayList(
                        materialController.getMateriales());

        tablaMateriales.setItems(materiales);
    }

    public Scene materialScene() {

        VBox contenedor = new VBox(tablaMateriales);

        Scene escena = new Scene(contenedor, 800, 600);

        return escena;
    }
}
```

### Comprender TableView

`TableView<Material>` representa una tabla que contiene objetos de tipo `Material`.

Cada `TableColumn` representa una columna de la tabla.

En este caso, tendremos una columna para el título y otra para la portada.

### ¿Qué hace PropertyValueFactory?

Cuando escribimos:

```java
new PropertyValueFactory<>("titulo")
```

le indicamos a JavaFX que obtenga el valor de la propiedad `titulo` del objeto. En nuestro modelo, puede obtenerlo mediante el método `getTitulo()`.

De forma equivalente, `"imagen"` permite recuperar el valor devuelto por `getImagen()`.

### ¿Qué es ObservableList?

JavaFX utiliza `ObservableList` en muchos de sus componentes. Es una lista que permite observar cambios en su contenido.

Aquí convertimos la colección que entrega el controlador en una lista observable:

```java
ObservableList<Material> materiales =
        FXCollections.observableArrayList(
                materialController.getMateriales());
```

Después asignamos esa lista a la tabla:

```java
tablaMateriales.setItems(materiales);
```

### Prueba 3

Ejecuta la pantalla de materiales.

En esta etapa, la columna «Portada» mostrará las direcciones de las imágenes como texto. Eso es normal: todavía no hemos indicado a JavaFX que debe dibujar una imagen en lugar de una cadena de texto.

Precisamente, ese será nuestro siguiente objetivo.

## 11. Mostrar imágenes reales en la TableView

Esta es la parte principal del laboratorio.

Actualmente, la columna de portada recibe una cadena de texto, por ejemplo:

```text
https://servidor/portada-hobbit.jpg
```

Pero queremos que el usuario vea la imagen que representa esa dirección.

Para hacerlo, necesitamos tres conceptos:

**Image:** representa la imagen que cargamos.

**ImageView:** es el componente visual que permite mostrar una imagen en JavaFX.

**TableCell:** representa una celda individual de la tabla. Podemos personalizarla para mostrar imágenes en lugar de texto.

### Paso 11.1. Importar las clases necesarias

En `MaterialView.java`, agrega estos imports:

```java
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
```

No elimines los imports que ya necesita la tabla.

### Paso 11.2. Personalizar la columna de portada

Busca el lugar donde creamos `columnaImagen`:

```java
TableColumn<Material, String> columnaImagen =
        new TableColumn<>("Portada");

columnaImagen.setCellValueFactory(
        new PropertyValueFactory<>("imagen"));
```

Debajo de esas líneas, agrega el siguiente código:

```java
columnaImagen.setCellFactory(columna -> {

    return new TableCell<Material, String>() {

        private final ImageView vistaImagen = new ImageView();

        {
            vistaImagen.setFitWidth(70);
            vistaImagen.setFitHeight(100);
            vistaImagen.setPreserveRatio(true);
        }

        @Override
        protected void updateItem(String rutaImagen, boolean vacio) {

            super.updateItem(rutaImagen, vacio);

            if (vacio || rutaImagen == null || rutaImagen.isBlank()) {

                setGraphic(null);
                setText(null);

            } else {

                Image imagen = new Image(rutaImagen, true);

                vistaImagen.setImage(imagen);

                setGraphic(vistaImagen);
                setText(null);
            }
        }
    };
});
```

### Paso 11.3. Ajustar la altura de las filas

En el método `configurarTabla()`, después de crear la tabla, agrega:

```java
tablaMateriales.setFixedCellSize(110);
```

Esto establece una altura fija de 110 píxeles para las filas, de manera que haya espacio para las portadas.

### ¿Qué hace setCellFactory?

Anteriormente utilizamos `setCellValueFactory` para indicar qué dato debía leer la columna.

Ahora utilizamos `setCellFactory` para definir cómo se representa ese dato visualmente.

Son dos responsabilidades distintas:

- `setCellValueFactory`: obtiene el dato del modelo.
- `setCellFactory`: construye la representación visual de cada celda.

En nuestro caso, el valor continúa siendo una cadena con la dirección de la imagen. Sin embargo, la celda personalizada utiliza esa dirección para cargar una imagen y mostrarla.

### Comprender updateItem

JavaFX llama a `updateItem` cuando necesita actualizar el contenido de una celda.

El parámetro `rutaImagen` contiene el valor que debe representarse.

El parámetro `vacio` indica si la celda está vacía.

Si no existe una imagen válida, limpiamos la celda:

```java
setGraphic(null);
setText(null);
```

Si existe una dirección, creamos una imagen:

```java
Image imagen = new Image(rutaImagen, true);
```

El segundo argumento permite cargar la imagen de forma asíncrona, evitando que la carga de una imagen remota tenga que bloquear la ejecución mientras espera la descarga.

Después asignamos la imagen al componente visual:

```java
vistaImagen.setImage(imagen);
```

Finalmente, usamos:

```java
setGraphic(vistaImagen);
setText(null);
```

`setGraphic()` coloca el componente visual dentro de la celda y `setText(null)` evita que aparezca la dirección como texto.

### ¿Por qué usamos ImageView?

`Image` representa el contenido de la imagen.

`ImageView` es el nodo visual que se incorpora a la interfaz gráfica para mostrarla.

Las siguientes instrucciones configuran su tamaño:

```java
vistaImagen.setFitWidth(70);
vistaImagen.setFitHeight(100);
vistaImagen.setPreserveRatio(true);
```

Las dos primeras establecen el tamaño máximo deseado para la visualización. La tercera mantiene las proporciones originales, evitando deformar las portadas.

### Prueba 4

Ejecuta la aplicación y abre la pantalla de materiales.

Comprueba lo siguiente:

- Los títulos siguen apareciendo correctamente.
- Las direcciones de las imágenes ya no aparecen como texto.
- Las portadas se muestran dentro de las celdas.
- Las imágenes mantienen sus proporciones.
- Las filas tienen suficiente altura.

Si una portada no aparece, revisa la dirección almacenada en el JSON.

La dirección debe apuntar directamente a un archivo de imagen accesible. Una URL que conduce a una página web de un libro no necesariamente es una URL de imagen.

## 12. Conectar la pantalla de materiales con HomeView

Ya tenemos una pantalla capaz de mostrar los materiales, pero necesitamos acceder a ella desde la pantalla principal.

En `HomeView.java`, creamos un botón y configuramos su evento.

Agrega los siguientes imports si todavía no existen:

```java
import javafx.scene.control.Button;
import javafx.stage.Stage;
```

Dentro de `homeUserDataSearchComponent()`, después de crear los componentes de búsqueda del usuario, agrega:

```java
Button btn_materiales = new Button("Ver materiales");

btn_materiales.setOnAction(e -> {

    MaterialView materialView = new MaterialView();

    Scene escenaMateriales = materialView.materialScene();

    Stage ventana = (Stage) btn_materiales
            .getScene()
            .getWindow();

    ventana.setScene(escenaMateriales);
});
```

Asegúrate de agregar el botón al contenedor que se devuelve:

```java
VBox vbox = new VBox(
        10,
        hbox_user_search,
        vbox_user_data,
        btn_materiales
);

return vbox;
```

Si ya tienes ese botón y el evento funcionando, no necesitas duplicarlo.

### ¿Qué ocurre cuando hacemos clic?

Primero creamos una instancia de `MaterialView`.

Después solicitamos la escena que esa vista construye:

```java
Scene escenaMateriales = materialView.materialScene();
```

Luego obtenemos la ventana que contiene el botón:

```java
Stage ventana = (Stage) btn_materiales
        .getScene()
        .getWindow();
```

Finalmente, reemplazamos la escena actual de esa ventana:

```java
ventana.setScene(escenaMateriales);
```

No estamos creando necesariamente una segunda ventana. Estamos cambiando la escena que se muestra en la ventana existente.

### Prueba 5

Ejecuta la aplicación completa.

1. Comprueba que se abre la pantalla principal.
2. Busca un usuario utilizando su RUT.
3. Presiona «Ver materiales».
4. Comprueba que se abre la tabla.
5. Verifica que se muestran los títulos y las portadas.

Si la búsqueda de usuarios ya estaba implementada, debe continuar funcionando. No necesitamos modificarla para agregar la tabla de materiales.

## 13. Estructura final del proyecto

Al terminar, la parte relevante del proyecto debería tener una estructura similar a esta:

```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── hachimakidev
│   │           └── biblioapp
│   │               ├── model
│   │               │   ├── Material.java
│   │               │   ├── Libro.java
│   │               │   ├── Revista.java
│   │               │   └── MaterialManager.java
│   │               ├── controller
│   │               │   └── MaterialController.java
│   │               └── view
│   │                   ├── HomeView.java
│   │                   └── MaterialView.java
│   └── resources
│       └── biblioteca.json
└── test
```

El proyecto completo también puede contener la clase principal de JavaFX, el módulo de usuarios y otros recursos.

## 14. Errores frecuentes y cómo resolverlos

**La aplicación no encuentra `biblioteca.json`.**

Comprueba que el archivo esté dentro de `src/main/resources` y que el nombre coincida exactamente con el que utiliza `getResourceAsStream()`.

**Aparece un error al leer un atributo del JSON.**

Revisa que todos los objetos contengan los atributos obligatorios que exige su tipo. Un libro debe tener `autor` y `paginas`; una revista debe tener `editorial` y `periodicidad`.

**Los títulos aparecen, pero las portadas no.**

Comprueba que la dirección sea una URL directa de imagen, que el archivo exista y que sea accesible. También puedes revisar los mensajes de error de la consola.

**La portada aparece deformada.**

Verifica que se haya configurado `setPreserveRatio(true)` en el `ImageView`.

**La tabla aparece vacía.**

Comprueba que `cargarMateriales()` se ejecute y que `MaterialManager` esté cargando los objetos correctamente.

**No se puede cambiar a la pantalla de materiales.**

Comprueba que el botón esté incorporado al contenedor de la vista y que el evento esté configurado correctamente.

## 15. Actividades de profundización

Una vez que la aplicación funcione, realiza estas modificaciones.

**Actividad 1: agregar materiales.**

Incorpora tres libros y dos revistas al archivo JSON. Verifica que aparezcan automáticamente en la tabla sin modificar las clases Java.

**Actividad 2: mejorar la tabla.**

Agrega una columna para el autor de los libros o la editorial de las revistas. Investiga cómo se comporta una `PropertyValueFactory` cuando una propiedad no existe en todos los objetos.

**Actividad 3: personalizar las portadas.**

Modifica el tamaño del `ImageView` y la altura de las filas. Compara los resultados.

**Actividad 4: investigar la herencia.**

Explica por qué podemos guardar un objeto `Libro` en una variable de tipo `Material`.

**Actividad 5: investigar MVC.**

Identifica qué clase lee el JSON, cuál obtiene los materiales para la vista y cuál se encarga de mostrar las imágenes.

## 16. Preguntas de evaluación

Responde con tus propias palabras.

1. ¿Qué problema resuelve la herencia entre `Material`, `Libro` y `Revista`?
2. ¿Cuál es la responsabilidad de `MaterialManager`?
3. ¿Qué función cumple Gson?
4. ¿Por qué usamos `ArrayList<Material>` para almacenar libros y revistas?
5. ¿Qué diferencia existe entre `TableView` y `ObservableList`?
6. ¿Qué información obtiene `PropertyValueFactory`?
7. ¿Cuál es la diferencia entre `setCellValueFactory` y `setCellFactory`?
8. ¿Qué diferencia existe entre `Image` e `ImageView`?
9. ¿Para qué sirve el método `updateItem`?
10. ¿Por qué la vista no debería encargarse directamente de interpretar el archivo JSON?
11. ¿Qué ocurre cuando se ejecuta `super(titulo, imagen)` en el constructor de `Libro`?
12. ¿Qué ventajas tiene guardar las direcciones de las imágenes en el JSON en lugar de escribirlas directamente en la interfaz?

## 17. Criterios de logro

Al finalizar, cada estudiante debería ser capaz de:

- Crear clases relacionadas mediante herencia.
- Leer datos JSON con Gson.
- Transformar registros JSON en objetos Java.
- Utilizar colecciones para almacenar objetos de distintas subclases.
- Aplicar una separación básica de responsabilidades mediante MVC.
- Construir una tabla JavaFX con `TableView` y `ObservableList`.
- Personalizar una celda mediante `TableCell` y `setCellFactory`.
- Mostrar imágenes con `Image` e `ImageView`.
- Conectar pantallas mediante eventos y escenas.

**Resultado esperado:** una aplicación JavaFX que carga libros y revistas desde JSON, los representa como objetos Java y presenta sus títulos y portadas en una tabla, manteniendo separadas las responsabilidades del modelo, el controlador y la vista.