# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 10

## Objetivos

* Examinar las características principales de los servicios y su forma de construcción.
* Reconocer el propósito del uso de Servicios dentro del proyecto para la obtención de información externa desde cualquier componente gráfico.
* Identificar las distintas funcionalidades que puede tomar un servicio para el manejo de información externa.
* Comprender la forma en que distintas partes de nuestro proyecto pueden dar uso de un servicio en común para obtener información.

# Antes de Comenzar

Para continuar con el ejercicio deberá actualizar la carpeta **resources/img/perfiles** ya que se han agregado nuevas imágenes. Estas las puede descargar en este mismo repositorio entrando a la carpeta **Clase10** seguido de **resources/img/perfiles**.

Vamos a observar mediante un modelo estructural general como es la estructura de nuestro proyecto hasta el momento:

<div align='center'>
    <img  src='https://i.imgur.com/8q6Uq6C.png'>
    <p>Modelo Estructural de nuestro Proyecto</p>
</div>

El anterior es un esquema general de como esta nuestro proyecto, hay muchas cosas que se han omitido y solo se esta mostrando lo más relevante de nuestro proyecto en cuanto a atributos, métodos y relaciones entre clases.

* Vamos a crear un nuevo paquete desde nuestra carpeta raíz **src** el cual llamaremos **archives** y ahi contendremos dos archivos que servirán como una simulación de información externa para el propósito de la clase. 

<div align='center'>
    <img  src='https://i.imgur.com/9Oo3FT6.png'>
    <p>Creación de paquete archives para contener archivos planos</p>
</div>

Estos archivos planos los puede encontrar en este mismo repositorio entrando a la carpeta **Clase10** seguido de la carpeta **src** y luego en la carpeta **archives**.

<div align='center'>
    <img  src='https://i.imgur.com/wOddQTb.png'>
    <p>Carpeta Clase10 dentro del repositorio</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/6oyuz11.png'>
    <p>Carpeta src dentro del repositorio</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/MPORiul.png'>
    <p>Carpeta archives dentro del repositorio</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/FFQuhag.png'>
    <p>Archivos planos dentro del repositorio</p>
</div>

Recordando un poco nuestro recorrido, hemos utilizado los eventos de mouse para darle interactividad a varias partes de nuestro proyecto, acciones como el arrasate de la ventana, el cambio de color de varios botones y labels, la gestión del contenido y color de los JTetField etc. Ademas mientras realizábamos dicha interactividad vimos algunos temas importantes relacionados con los eventos, cosas como: **Representación única para objetos gráficos de una misma Clase** , **Discriminación de Clases**, **Efectos hacia otros objetos Gráficos** y el **Uso combinado de varios Métodos implementados de eventos**.

# Servicios 

En esta sesión vamos a ver el uso y características de los servicios y para ello vamos a ver algunos items como:
* **Explicación general de los servicios**.
* **Servicio contenedor de información externa**.
* **Servicio que recibe información externa**.
* **Ajustes de nuestro proyecto para el uso de servicios**.


# Explicación general de los servicios

Antes hemos hablado de los **Componentes Gráficos** y sabemos hasta este punto que estos están conformados por una parte visual (**Template**), y una parte lógica (**Component**). Aunque la clase **Component** utiliza lógica detrás para que todas las funcionalidades del componente se puedan cumplir, hay acciones que no deben realizar, recordemos que esta clase se debe encargar unicamente de soportar la lógica que requiere el componente.

Nuestras aplicaciónes de interfaz Gráfica siempre van a depender de la obtención de información externa que el usuario va a solicitar, ya sea de un servidor web, una base de datos externa, una Api etc. Podríamos delegar la obtención de esta información a nuestras clases **Components** y es una forma de hacerlo y funciona. Sin embargo esto va a restar reutilización y modularidad a nuestro proyecto. Esta información externa tiene ciertos datos que seguramente van a ser solicitados por varias partes de nuestro proyecto y si en cada componente que se necesite dicha información se va a consumir un mismo servicio web externo esto podría restar rendimiento.

Otra forma para obtener esta información sin afectar el rendimiento es obtener la información externa una vez y empezar a pasar la información entre componentes, pero esta practica hará que nuestro proyecto esté muy acoplado y exista una dependencia alta entre componentes gráficos.

Podemos hacer uso de servicios que van a tener un propósito estrecho y bien definido y que puede encapsular alguna funcionalidad o información externa que será requerida por varias partes de nuestro proyecto. Estas clases entonces se encargan principalmente de contener la información que se va a obtener externamente. Pueden existir varios tipos de servicios:
* **Servicios Lógicos:** Que se encargan de contener información externa y que sera solicitada por varas partes de nuestro proyecto. Siempre serán solicitados por las clases **Component** de nuestros componentes.
* **Servicios Gráficos:** Que contiene una funcionalidad o información especifica y relacionada con la creación de objetos gráficos. Siempre serán solicitados por las clases **Template**.

Independientemente de su propósito la estructura de un servicio es similar, podemos crear servicios **Singleton** o podemos realizar varios objetos de un servicio (Esto en ocasiones muy especiales).

Hemos visto a lo largo del curso el uso de **Servicios Gráficos**, hasta el momento hemos creado:
* El servicio Gráfico **ObjGraficosService** encargado de encapsular la construcción de objetos gráficos.
* El servicio Gráfico **RecursosService** encargado de la creación responsable de objetos decoradores que puedan ser solicitados por varios template.

En los anteriores servicios podemos ver los dos propósitos principales de los servicios:

* **Contener Funcionalidad**: En el servicio **ObjGraficosService** tenemos encapsulada una funcionalidad que es usada por la mayoría de clases Template que hemos creado. Este servicio contiene una serie de métodos encargados de la construcción de objetos gráficos y que ayuda a las clases **Template** con el proceso de **creación de objetos gráficos**. Esta funcionalidad es común y general por lo que permite que se use en varias partes del proyecto e incluso en varios proyectos.
* **Contener Información**: En el servicio **RecursosService** de alguna manera se esta conteniendo información, no es información externa evidentemente ni tampoco información compuesta de datos comúnes. Pero desde una perspectiva general este servicio crea objetos decoradores (información) que sera solicitada desde varias partes de nuestro proyecto.

Ahí esta la clave del uso de los servicios, estos son solicitados por varias partes del proyecto y evita el acoplamiento extremo entre componentes gráficos. Por ejemplo si el servicio **RecursosService** no existiera pero se quisiera controlar la creación de objetos decoradores como colores, fuentes etc. La única forma de realizar esto es creándolos todos desde la clase **App** y empezar a repartir estos objetos mediante los constructores de los componentes y esto generaría un altísimo acoplamiento entre clases.

## Antes de implementar servicios

Vamos a crear dos carpetas

# Servicio contenedor de información externa

Hay veces que la información externa es necesaria solo para propósitos visuales, es decir información que sera mostrada pero que no necesariamente esta contenida en algún servidor web o una base de datos, simplemente es información que se quiere mostrar en la interfaz gráfica pero que no es tan importante para guardarse en una base de datos o un servidor web externo.

En este caso vamos a contener información relacionada con las acciónes que se muestran en el componente **inicio** al el usuario. Como recordaran estas acciónes se muestran a traves de la **reutilización de componentes gráficos** pero había una cantidad considerable de código debido a esta reutilización, esta vez vamos a recibir la información de las acciones a traves de un archivo plano (Simulación de información externa) y lo vamos a contener en un servicio para que podamos reducir un poco el código de nuestra clase.

<div align='center'>
    <img  src='https://i.imgur.com/7r5EHeu.png'>
    <p>Acciones dentro del componente Inicio.</p>
</div>

Primero vamos a mostrar la estructura de nuestro archivos plano acciones:
<div align='center'>
    <img  src='https://i.imgur.com/K3mJU5L.png'>
    <p>Estructura de archivo plano, acciones</p>
</div>

Pueden notar que el archivo plano contiene cierta información sobre cada acción y se debe resaltar que cada parte de la información por acción esta separada por una coma **(,)** y cada acción esta separada por un salto de linea. Entre sus partes están:
* **Titulo de Acción**
* **Descripción de Acción** Note que esta insertada de una vez la propiedad html para que el texto este centrado y con saltos de linea.
* **Dirección de Imagen**

Ahora vamos a crear nuestro servicio **AccionService**, esta clase la creamos dentro del paquete **Services**:

<div align='center'>
    <img  src='https://i.imgur.com/FxT01bH.png'>
    <p>Creación de servicio AccionService</p>
</div>

Para tener un control de la creación del objeto del servicio vamos a implementar el patron **Singleton** de igual manera, sin embargo vamos a dejar una pequeña variación y es que el constructor va a quedar publico. Esto debido a que no se sabe en que momento alguna parte del proyecto va a necesitar un objeto del servicio en el cual no se le haya cargado ninguna información previamente y creara una ejemplificación del servicio para sus propósitos individuales, pero si necesita información que puede ser compartida entre varios componentes de seguro preferirá obtener el objeto del servicio que están usando los demás a traves del singleton.


* Primero se declará un objeto de si mismo y recordemos que debe ser un atributo tipo **static**.
```javascript
    private static AccionService servicio;
```

* Se crea el método estático para la creación única del servicio: 

```javascript
public static AccionService getService(){
    if(servicio == null){
        servicio = new AccionService();
    }
    return servicio;
}
```

* Como dijimos el constructor lo vamos a dejar publico (es decir en su forma normal) por los casos que explicamos antes.

Antes de continuar con nuestro servicio vamos a crear otro paquete desde la carpeta raíz **src**, la cual llamaremos **models**, adentro vamos  a crear una clase llamada **Accion** y sera la representación en objeto de las acciones. 

<div align='center'>
    <img  src='https://i.imgur.com/usKErN0.png'>
    <p>Creación de paquete models y la clase Accion.java</p>
</div>

* Dentro de esta clase vamos a declarar los atributos:
```javascript
// Dentro de la clase Accion
private String nombreAccion;
private String descripcionAccion;
private ImageIcon imagenAccion;
```
* Ahora vamos a generar los métodos **get y set** correspondientes a sus atributos:

```javascript
public String getNombreAccion (){
    return nombreAccion;
}

public String getDescripcionAccion (){
    return descripcionAccion;
} 

public ImageIcon getImagenAccion (){
    return imagenAccion;
}

public void setNombreAccion (String nombreAccion){
    this.nombreAccion = nombreAccion;
}

public void setDescripcionAccion (String descripcionAccion){
    this.descripcionAccion = descripcionAccion;
}

public void setImagenAccion (ImageIcon imagenAcciIcon){
    this.imagenAccion = imagenAcciIcon;
}
```

***Nota:** El anterior es un esquema general de una clase del modelo. Es bueno manejar la información por medio de objetos que encapsulen la información y es un pilar fundamental en la programación orientada a objetos*

Ahora nos vamos a ubicar nuevamente en el servicio **AccionService**, este servicio va a contener un atributo que sera una lista de todas las acciones necesarias dentro del proyecto, para hacer esto necesitamos un objeto tipo **arrayList**, este es un tipo de arreglo que tiene ciertas ventajas, por ejemplo puede contener objetos y ademas no tiene un tamaño fijo por lo que puede agregarse o quitarse objetos dentro de la lista en tiempo de ejecución:

* **Declaración:**
```javascript
// Dentro del servicio AccionService
private ArrayList<Accion> acciones;
```

* **ejemplificación:**
```javascript
public AccionService() {
    acciones = new ArrayList<Accion>();
}
```

***Nota:** Entre los signos **<>** esta el tipo de objetos que va a contener, en este caso nuestro arreglo **acciones** va a contener objetos de tipo **Accion**, recordemos que aunque es dinámico, al ser un arreglo solo puede contener un solo tipo de dato.*

Como este servicio se va a encargar de obtener la información externa, tenemos que crear un método encargado de recibir la información del archivo plano. Recordemos que estamos en un caso en el que la información externa no proviene de ninguna fuente de almacenamiento externa sino que estamos suponiendo que es información que solo se esta necesitando en el Frontend para fines visuales.

```javascript
public void cargarDatos(){
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    try {
        archivo = new File ("Clase10/src/archives/aciones.txt");
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);

        String linea;
        while((linea=br.readLine())!=null){
            String[] atributos = linea.split(",");
            Accion accion = new Accion();
            accion.setNombreAccion(atributos[0]);
            accion.setDescripcionAccion(atributos[1]);
            accion.setImagenAccion(new ImageIcon(atributos[2]));
            acciones.add(accion);
        }
        fr.close(); 
    }
    catch(Exception e){
        e.printStackTrace();
    }
}
```
El anterior código se encarga de leer la información contenida en el archivo plano **acciones.txt**, una vez se ha leído una linea dentro del archivo se crea un objeto de la acción y se inserta en la lista de acciones. La explicación de este método no es el fin de esta clase pero vamos a explicar a grandes rasgos que hace.

<div align='center'>
    <img  src='https://i.imgur.com/yplcQvL.png'>
    <p>Explicación de código de obtención de información del archivo plano</p>
</div>

Para que la información pueda ser cargada una vez se obtenga el servicio vamos a llamar este método desde el constructor:

```javascript
public AccionService() {
    acciones = new ArrayList<Accion>();
    cargarDatos();
}
```

Ahora vamos a crear un método encargado de retornar una accion cuando algún componente gráfico lo necesite:

* El método debe retornar un objeto tipo **Accion** y debe recibir por parámetro un entero que representa la posición en el arreglo de la accion que necesitamos.

```javascript
public Accion devolverAccion(int posicion){
}
```

* Vamos a realizar el contenido mediante un **try/catch**. Esto se realizar para el control de errores. Vamos a intentar retornar un objeto **Accion** dentro del try, en caso de que se genere un error  en el momento de retornar una acción podemos gestionar el error a traves del catch.

```javascript
public Accion devolverAccion(int posicion){
    try{
    }
    catch(Exception e){
    }
}
```
* Dentro del Try vamos a retornar la acción dentro de la lista que se pida mediante la posición enviada como argumento, en dado caso en que la posición no exista dentro del arreglo, vamos a retornar un null.

```javascript
public Accion devolverAccion(int posicion){
    try{
        return acciones.get(posicion);
    }
    catch(Exception e){
        return null;
    }
}
```

Nuestro servicio esta listo y puede usarse en varias partes de nuestro proyecto, por ahora lo vamos a usar en un lugar especifico, el componente **Inicio**. Nos ubicamos en la clase **InicioComponent** y lo primero que debemos hacer es obtener el servicio:

* **Declaración:**

```javascript
private AccionService sAccion;
```

* **Obtención del Servicio:**

```javascript
// Dentro del constructor
sAccion = AccionService.getService();
```

Una vez obtenemos el servicio de acciones, vamos a crear un método que se comunique con el servicio y que de igual forma retorne una **Accion** que obtiene del Servicio:

* De igual forma retornara un objeto tipo **Accion** y recibe por parámetro la posicion de la acción en el arreglo dinámico.

```javascript
public Accion obtenerAccion(int numeroAccion){

}
```

* Ahora debemos llamar al método creado en el servicio **AccionService** pasándole como argumento la posicion de la acción que se recibió como parámetro:

```javascript
public Accion obtenerAccion(int numeroAccion){
    return sAccion.devolverAccion(numeroAccion);
}
```

Ahora nos ubicamos en la clase **InicioTemplate** y recordamos un poco como esta escrito el código del método **crearContenidoPAcciones()**, en el teníamos la creación de cada componente **Accion** de forma manual.

<div align='center'>
    <img  src='https://i.imgur.com/PUtZqXf.png'>
    <p>Creación manual del Componente Gráfico Accion</p>
</div>

Bueno, ahora que tenemos la información de todas las acciones, podemos automatizar la creación del componente gráfico **Acción** varias veces (Aprovechando también la reutilización de componentes). Vamos a borrar la creación manual de todas las acciones y dejamos la creación del título intacta:

```javascript
public void crearContenidoPAcciones(){
    this.lAcciones = sObjGraficos.construirJLabel(
        "Nuestros Servicios", 10, 10, 160, 30, null, sRecursos.getColorAzul(), 
        null, sRecursos.getFontTitulo(), "c"
    );
    this.pAcciones.add(lAcciones);
}
```

Vamos ahora a crear un **Contador** esta variable nos va a servir como la posición a buscar dentro del arreglo de acciones que contiene nuestro servicio **AccionService**:

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...

    int numeroAccion=0;
}
```

Vamos a crear un objeto de tipo **Accion** (que se encuentra en nuestra carpeta **Models**) y lo vamos a igualar con la llamada del método que esta en la clase **Component** que a su vez va a llamar al método que se encuentra en el servicio **ActionService**:

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    
    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
}
```

En el anterior código hemos llamado al método **obtenerAccion** de la clase **InicioComponent** y como argumento le pasamos nuestro **Contador** que vale 0, esto quiere decir que hemos obtenido desde el servicio **AccionService** la **acción** que se encuentra en la primera posición del arreglo **acciones**.

Vamos a crear ahora un ciclo **while** y la condición para que el ciclo continue es que al llamar al servicio para obtener una accion esta no sea **nulo** o en otras palabras que una vez ya no existan mas acciones en el arreglo el ciclo se termine.

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    
    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while(accion != null){
          
    }
}
```

Como ya recibimos nuestra primera accion antes de entrar al ciclo la condición se cumple y puede entrar, adentro vamos a crear nuestro componente gráfico **Accion** y como sabemos debemos llamar a su clase **AccionComponent** para acceder a este.

* Primero se ejemplifica la clase **Component**:

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    
    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while(accion != null){
        AccionTemplate pAccion= new AccionComponent();
    }
}
```

***Nota:** Pueden observar que creamos un objeto tipo Template del Componente Accion pero lo estamos igualando a la ejemplificación de la clase Component, esto claramente va a traer error pero esto se hace por que de una vez en la ejemplificación vamos a traer la clase template y así la igualdad será cierta.*

Si recordamos un poco, la clase **AccionComponent** pide ciertas cosas por parámetros como **una imagen, un título y un párrafo de descripción**. 

<div align='center'>
    <img  src='https://i.imgur.com/QhRZQGd.png'>
    <p>Parámetros exigidos por el componente Acción</p>
</div>

Debemos pasarle esos argumentos y para eso usaremos nuestro objeto **accion**, vamos a llamar a sus métodos **get** correspondientes (**getImagenAccion(), getNombreAccion(), getDescripcionAccion()**) que ya contienen esta información necesaria.

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    
    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while(accion != null){
        AccionTemplate pAccion= new AccionComponent(
            accion.getImagenAccion(), accion.getNombreAccion(), accion.getDescripcionAccion()
        );
    }
}
```

Ahora el editor muy seguramente nos debe estar sacando error y esto es por que la igualdad **Clase Template = Clase Component** no es verdadera, debemos llamar a la clase **Template** del componente para que esta igualdad se cumpla:

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    
    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while(accion != null){
        AccionTemplate pAccion= new AccionComponent(
            accion.getImagenAccion(), accion.getNombreAccion(), accion.getDescripcionAccion()
        ).getAccionTemplate();
    }
}
```

Nuestro componente **Accion** ya ha sido ejemplificado de manera exitosa, sin embargo debemos recordar que estamos realizando una **reutilización por posicionamiento** esto quiere decir que debemos darle la posición a cada componente acción que llamemos. Como en este caso sabemos que son 6 componentes que realizaremos, vamos a realizar el siguiente calculo:

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    

    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while(accion != null){
        AccionTemplate pAccion= new AccionComponent(
            accion.getImagenAccion(), accion.getNombreAccion(), accion.getDescripcionAccion()
        ).getAccionTemplate();
        if(numeroAccion <=2)
            pAccion.setLocation(15 + numeroAccion * pAccion.getWidth() + numeroAccion * 15 , 50);
        else
            pAccion.setLocation(15 + (numeroAccion - 3) * pAccion.getWidth() + (numeroAccion - 3) * 15 , 190);
    }
}
```

Para calcular la posición de cada uno de los componentes de **Accion** que vamos a mostrar debemos mirar la perspectiva de los dos ejes:
* Para el eje X nos vamos a ayudar de los valores del **Contador y El Ancho del Componente Gráfico Acción** de esta forma nuestro primera acción se ubicara en la posicion 15 del eje X y a medida que se añaden los otros componentes se le suma el ancho y un espacio de 15 relacionado con el contador.
* Para el eje Y nos ayudamos del uso del **If / Else** esto debido a que solo nos estamos guiando de una variable **El contador** para la posición de los componentes en el **if** se ubicaran los componentes **Accion** de la primera fila y en el **else** los de la segunda.

En caso de no tener claro el numero de componentes **Accion** a mostrar en pantalla seguramente habrá que realizar el uso de otra variable que ayude a automatizar la posicion del eje Y sin el uso de **If / Else**.

Para finalizar el ciclo de manera correcta debemos:
* Agregar el componente a nuestro panel.
* Aumentar el Contador.
* Llamar al siguiente objeto de acción.

```javascript
public void crearContenidoPAcciones(){
    // Creación del Titulo
    ...
    

    int numeroAccion=0;
    Accion accion = inicioComponent.obtenerAccion(numeroAccion);
    while(accion != null){
        AccionTemplate pAccion= new AccionComponent(
            accion.getImagenAccion(), accion.getNombreAccion(), accion.getDescripcionAccion()
        ).getAccionTemplate();
        if(numeroAccion <=2)
            pAccion.setLocation(15 + numeroAccion * pAccion.getWidth() + numeroAccion * 15 , 50);
        else
            pAccion.setLocation(15 + (numeroAccion - 3) * pAccion.getWidth() + (numeroAccion - 3) * 15 , 190);
        this.pAcciones.add(pAccion);
        numeroAccion ++;
        accion = inicioComponent.obtenerAccion(numeroAccion);
    }
}
```

Nuestras Acciones están listas y se muestran correctamente, podemos ejecutar la aplicación y verificar:

<div align='center'>
    <img  src='https://i.imgur.com/HQUhJfh.png'>
    <p>Componentes Accion creados de forma automática.</p>
</div>

Ya realizamos la automatización de la creación de **componentes gráficos reutilizables** y es un complemento de lo que habíamos visto previamente sobre ese tema. Sin embargo vale la pena aclarar que el fin del servicio **AccionService** no es solamente poder realizar esta automatización, como lo explicamos previamente es para contener la información de estas acciónes que se puedan usar en varias partes de nuestro proyecto. Una excelente forma de usar este servicio en otra parte de nuestro proyecto es creando otro componente por ejemplo que muestre mas información de cada acción y una vez se oprima en cualquiera de los paneles de acción que mostramos en pantalla, este abra ese nuevo componente. De esta forma no tenemos que realizar un traspaso de información entre componentes sino que el nuevo componente puede llamar al servicio para obtener la información de la acción que necesita. 

Esta acción no se realizará en el momento, pero para explicar lo anterior referente al uso compartido de servicios lógicos vamos a ver en la siguiente sección el uso de otro servicio que si nos conviene realizar y que se necesita en varias partes del proyecto.

***Nota:** Como ya realizamos la automatización de los paneles de accion podemos borrar las imágenes que habíamos creado en la clase **InicioTemplate** y que contenía estas imágenes ya que no las estamos usando mas, de esta forma el método **crearObjetosDecoradores()** queda asi:*

<div align='center'>
    <img  src='https://i.imgur.com/ghthPcs.png'>
    <p>Método crearObjetosDecoradores sin las imágenes de acción</p>
</div>

# Servicio que recibe información externa

Este tipo de servicios no contienen la información externa directamente, sino que deben obtenerla desde algún sistema alejo primero, estos pueden ser algún servidor externo. alguna base de datos externa etc. Para este caso vamos a manejar la información de los usuarios que ya están registrados en el sistema y que solo ellos pueden entrar. Esta información esta contenida en el archivo plano **usuarios.txt**.

<div align='center'>
    <img  src='https://i.imgur.com/Qz1rnIs.png'>
    <p>Información contenida de los usuarios</p>
</div>


Vemos que la información incluye el **Nombre del usuario, clave del usuario, tipo usuario e imagen usuario.** Para aclarar normalmente las contraseñas se guardan en las bases de datos de forma encriptada, sin embargo esos son temas del Backend que este curso no va a tocar,  en este caso solo se simula un ejemplo de información externa.

Igual que como hicimos con las acciones vamos a crear un objeto en el modelo que representara el objeto de los usuarios:

<div align='center'>
    <img  src='https://i.imgur.com/GX0KBi2.png'>
    <p>Creación de clase Usuario dentro del paquete Models</p>
</div>

Dentro de la clase **Usuario** vamos a crear los atributos de un usuario que ya describimos anteriormente y ademas de sus respectivos métodos **set y get**.

```javascript
public class Usuario {
    private String nombreUsuario;
    private String claveUsuario;
    private String tipoUsuario;
    private ImageIcon imagenUsuario;

    public String getNombreUsuario (){
        return nombreUsuario;
    }

    public String getClaveUsuario (){
        return claveUsuario;
    }

    public String getTipoUsuario (){
        return tipoUsuario;
    }

    public ImageIcon getImagenUsuario (){
        return imagenUsuario;
    }

    public void setNombreUsuario (String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public void setClaveUsuario (String claveUsuario){
        this.claveUsuario = claveUsuario;
    }
    
    public void setTipoUsuario (String tipoUsuario){
        this.tipoUsuario = tipoUsuario;
    }

    public void setImagenUsuario (ImageIcon imagenUsuario){
        this.imagenUsuario = imagenUsuario;
    }
}
```

Ahora vamos a simular un controlador de información externa de forma local, (esto por motivos de ejemplo) pero recuerden que normalmente nuestra aplicación de interfaz gráfica se comunicara con Apis externas.

Vamos a crear un nuevo paquete desde la carpeta raíz **src** la cual llamaremos **logic** y dentro crearemos una clase llamada **ControlUsuarios**, esta sera la clase encargada de obtener, contener y gestionar la información.

<div align='center'>
    <img  src='https://i.imgur.com/4VzVvr1.png'>
    <p>Creación del paquete Logic y su controlador.</p>
</div>

Como el controlador va a contener la información esta va a obtenerla a traves del archivo plano:

* Se declara una lista de Usuarios:
```javascript
// En la clase ControlUsuarios
private ArrayList<Usuario> usuarios;
```

* Se ejemplifica el arreglo dinámico:

```javascript
// Dentro del Constructor
usuarios = new ArrayList<Usuario>();
```

* Se crea el método para cargar la información del archivo plano:
```javascript
public void cargarDatos(){
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    try {
        archivo = new File ("Clase10/src/archives/usuarios.txt");
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);

        String linea;
        while((linea=br.readLine())!=null){
            String[] atributos = linea.split(",");
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(atributos[0]);
            usuario.setClaveUsuario(atributos[1]);
            usuario.setTipoUsuario(atributos[2]);
            usuario.setImagenUsuario(new ImageIcon(atributos[3]));
            usuarios.add(usuario);
        }
        fr.close(); 
    }
    catch(Exception e){
        e.printStackTrace();
    }
}
```

* Se llama al método desde el constructor:

```javascript
// Dentro del Constructor
cargarDatos();
```

Ahora vamos a crear el servicio **UsuarioService**

<div align='center'>
    <img  src='https://i.imgur.com/QBsc6T8.png'>
    <p>Creación del servicio UsuarioService</p>
</div>

Realizamos la estructura básica del servicio para ser obtenido:

* Declaración de la referencia estática a si mismo: 
```javascript
// Dentro del Servicio UsuarioService
private static UsuarioService servicio;
```

* Creamos el método estático para el control de una sola ejemplificación:

```javascript
public static UsuarioService getService(){
    if(servicio == null)
        servicio = new UsuarioService();
    return servicio;
}
```

* El constructor lo dejamos publico como ya explicamos en la anterior sección.

* Este servicio va contener dos atributos que serán de vital importancia, un atributo sera el objeto del controlador para que exista una comunicación hacia ese controlador, la otra sera un string que contiene que usuario sera logeado y se explicara su funcionalidad más adelante.

* **Declaración:**
```javascript
// Dentro del Servicio UsuarioService
private ControlUsuarios cUsuario;
private String usuarioLogeado;
```

* **Ejemplificación**

```javascript
// Dentro del Constructor
cUsuario = new ControlUsuarios();
```

## Validación de usuarios

Para realizar la validación se va a verificar tres cosas:
* El nombre del usuario.
* Contraseña del usuario.
* Tipo del usuario.

Para que una persona pueda ingresar a nuestra aplicación debe proporcionar esos tres datos y estos deben estar guardados en el lugar de persistencia de datos (En este ejercicio el archivo plano), ademas de eso los 3 datos deben coincidir para un mismo usuario, de lo contrario no podrá entrar. 
Sin embargo esta gestión de verificación del usuario no la debe realizar la parte Frontend de un proyecto, normalmente esto lo realiza el Backend y el proyecto del cliente solo recibe la respuesta de la validación una vez enviá los datos que el usuario ha proporcionado.

Para simular esto vamos a realizar la respectiva validación dentro de la clase **ControlUsuarios** que recordemos es una clase externa de nuestro proyecto y solo la usamos por motivos de simulación de una aplicación real.

Dentro de esta clase vamos a crear el método **VerificarUsuario** el cual va a recibir por parámetros 
* **String nombreUsuario**
* **String claveUsuario** 
* **String tipoUsuario**

Y va a retornar un **Boolean** que indicara si la validación fue correcta o no.
```javascript
public boolean verificarUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario){

}
```

Dentro de este método vamos a recorrer el arreglo de usuarios llamado **usuarios** esto mediante el siguiente ciclo:

```javascript
public boolean verificarUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario){
    for(Usuario usuario : usuarios){
    
    }
}
```

En el anterior ciclo estamos recorriendo cada posición del arrayList pero en vez de usar un **Contador** que pase por cada posición estamos usando directamente una variable objeto de tipo **Usuario** (creado en el modelo). Que se va a convertir directamente en cada objeto que esta en la posición que se esta revisando actualmente. Dentro de ese ciclo realizamos la respectiva validación:
```javascript
public boolean verificarUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario){
    for(Usuario usuario : usuarios){
        if(usuario.getNombreUsuario().equals(nombreUsuario))
            if(usuario.getClaveUsuario().equals(claveUsuario))
                if(usuario.getTipoUsuario().equals(tipoUsuario))
                    return true;
    }
    return false;
}
```

En la anterior validación estamos indicando que si en algún momento del recorrido del arreglo encuentra un objeto **Usuario** que cumple con la igualdad de los 3 datos dados por el usuario, se va a retornar un **True** indicando que se ha realizado la validación satisfactoriamente, en caso de no encontrar ningún objeto **Usuario** que cumpla con los requisitos se saldrá del ciclo y retornara un **False** indicando que el usuario no existe en el sistema.

Ahora vamos a ir a la clase de nuestro servicio **UsuarioService** que es la clase dentro de nuestro proyecto que se va a comunicar directamente con el control externo. Allí vamos a crear un nuevo método al cual llamaremos **verificarDatosUsuario** el cual recibirá de nuevo los tres datos que el usuario proporcionará y de igual forma retornara un **boolean**.

```javascript
public boolean verificarDatosUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario){

}
```

Dentro vamos a realizar el llamado del método que validara los datos dentro del control y en caso de que la validación sea efectiva vamos a realizar una acción de suma importancia.

```javascript
public boolean verificarDatosUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario){
    if(cUsuario.verificarUsuario(nombreUsuario, claveUsuario, tipoUsuario)){
        this.usuarioLogeado = nombreUsuario;
        return true;
    }
    return false;
}
```

Noten que dentro del **If** hemos llamado al método del Controlador, en caso de que este retorne un **True** va a entrar dentro de las instrucciones dadas dentro de el, en este caso será que si el usuario efectivamente proporciono los datos de un usuario registrado va a igualar el atributo **usuarioLogeado** con el nombre del usuario que escribió el cliente.
En caso de que la verificación haya retornado un **False** este método de igual forma retornara el **False**.


Nuestro Servicio esta listo para realizar la verificación ahora es tiempo de ir a nuestro componente **Login** y hacer uso de este servicio. Lo primero que vamos a realizar es la obtención del servicio **UsuarioService** desde nuestro componente, específicamente desde la clase **LoginComponent**. 

* **Declaración:**

```javascript
// Dentro de la clase LoginComponent
private UsuarioService sUsuario;
```

* **Obtención de Servicio:**
```javascript
// Dentro del constructor
sUsuario = UsuarioService.getService();
```

Recordando un poco, una vez el usuario oprime el botón de entrar dentro del Login el usuario podia ver un mensaje emergente que le indicaba los datos que había proporcionado y después entraba a la ventana principal.

<div align='center'>
    <img  src='https://i.imgur.com/83tkod4.png'>
    <p>Evento una vez se oprime el botón entrar del Login</p>
</div>

Bueno ahora vamos a cambiar un poco esa funcionalidad, ya no vamos a mostrar esos datos, eso solo se hizo como una prueba para ver como se realizaba el recibimiento de información desde la parte gráfica. Para empezar vamos a cambiar el nombre del método **mostrarDatosUsuario** por **enviarDatosUsuario**, y vamos a quitar el llamado del método entrar, ya que ahora solo podrá entrar en el caso de que el usuario este registrado.

```javascript
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginTemplate.getBEntrar())
        this.enviarDatosUsuario();
    ...
```
```javascript
public void enviarDatosUsuario() {
    ...
}
```

Dentro del método enviarDatosUsuario vamos a dejar la obtención de los datos de las dos cajas de texto y del combobox, el resto no será necesario.

```javascript
public void enviarDatosUsuario() {
    String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
    String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
    String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
}
```

Ahora vamos a enviar los datos a nuestro servicio **UsuarioService** para realizar la respectiva validación, en caso de ser verificado satisfactoriamente mostrará un mensaje indicando el ingreso exitoso y entrara a la vista principal, en el caso contrario mostrará un mensaje indicando que algo ha salido mal.

```javascript
public void enviarDatosUsuario() {
    String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
    String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
    String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
    if(sUsuario.verificarDatosUsuario(nombreUsuario, claveUsuario, tipoUsuario)){
        JOptionPane.showMessageDialog(null, "Ingreso Exitoso", "Advertencia", 1);
        entrar();
    }
    else
        JOptionPane.showMessageDialog(null, "Algo quedo mal", "Advertencia", 2);
}
```
Una vez ejecutamos nuestro programa podemos verificar su funcionamiento:

<div align='center'>
    <img  src='https://i.imgur.com/lc3L4ko.png'>
    <p>Caso exitoso de ingreso de usuario</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/k3kSlRi.png'>
    <p>Caso fallido de ingreso de usuario</p>
</div>

Ahora queremos que una vez el usuario haya entrado exitosamente los datos de este como el nombre del usuario y la foto del perfil se vean en la navegación del usuario, por el momento solo hemos utilizado una imagen y un texto de forma estática:

<div align='center'>
    <img  src='https://i.imgur.com/gdH5Mdz.png'>
    <p>Información estática del usuario</p>
</div>

Para cambiar esta información vamos a llamar al servicio **UsuarioService** desde el componente **NavegaciónUsuario** para conseguir los datos del usuario que se ha logueado. Pero primero vamos a configurar al controlador **ControlUSuarios** creando un método que retorne a un usuario de acuerdo a su nombre, vamos entonces a la clase **ControlUsuarios** y realizamos el siguiente método:

```javascript
// Dentro de la clase ControlUsuarios
public Usuario devolverUsuario(String nombreUsuario){
    for(Usuario usuario : usuarios){
        if(usuario.getNombreUsuario().equals(nombreUsuario))
            return usuario;
    }
    return null;
}
```

En el anterior método **devolverUsuario** recibimos por parámetro un String que representa el nombre del usuario y va a recorrer el arreglo de usuarios, en caso de que un objeto **Usuario** coincida en su nombre con el que se envió por parámetro va a retornar ese objeto, en el caso contrario retornara un null.

Ahora dentro del servicio **UsuarioService** crearemos un método para poder obtener dicho usuario:

```javascript
// Dentro del servicio UsuarioService
public Usuario getUsuarioLogeado(){
    return cUsuario.devolverUsuario(usuarioLogeado);
}
```

En este caso le vamos a enviar como argumento al método del controlador el atributo **usuarioLogeado** que si recordamos, le habíamos dado un valor justo en el momento en que el login se verifico de forma exitosa:

<div align='center'>
    <img  src='https://i.imgur.com/kP5PTWp.png'>
    <p>Momento en el que se le dio valor al atributo usuarioLogeado</p>
</div>

Mas adelante se explicara por que se realiza esta metodología, por ahora vamos a hacer uso de nuestro servicio.

Dentro de la clase **NavegacionUsuarioComponent** vamos a realizar la obtención del servicio;

* **Declaración:**
```javascript
// Dentro de la clase NavegacionUsuarioComponent
private UsuarioService sUsuario;
```

* **Obtención del servicio:**
```javascript
// Dentro del Constructor
this.sUsuario = UsuarioService.getService();
```

Vamos a crear un atributo que se llamara **usuarioLogeado** y sera de tipo **Usuario**, una vez declarado, usaremos el servicio **UsuarioService** para obtener el objeto del usuario logueado. 

* **Declaración:**
```javascript
// Dentro de la clase NavegacionUsuarioComponent
private Usuario usuarioLogeado;
```

* **Obtención del objeto del usuario que ha ingresado:**
```javascript
// Dentro del constructor
this.usuarioLogeado = sUsuario.getUsuarioLogeado();
```
Finalmente vamos a crear su método **get**, esto para que pueda ser obtenido por su clase compañera **Template**:

```javascript
public Usuario getUsuario(){
    return this.usuarioLogeado;
}
```

Finalmente nos ubicamos dentro de la clase **NavegacióUsuarioTemplate** y vamos a realizar unos pequeños cambios con los label que muestran la imagen y nombre del usuario:

<div align='center'>
    <img  src='https://i.imgur.com/ViQ0RWf.png'>
    <p>Cambios en los labels dentro del método crearJLabels de la clase NavegacionUsuarioTemplate</p>
</div>

Noten que en el caso del Label **lNombreUsuario** Ahora enviamos como argumento en el texto al nombre del usuario que obtenemos a traves de la clase **Component**. Lo mismo pasa con la imagen, cuando la vamos a redimensionar llamamos a la imagen del objeto del usuario que obtenemos a traves de la clase **Component**.

Vamos a realizar unas pruebas a ver que sucede:

<div align='center'>
    <img  src='https://i.imgur.com/2dfZVvD.png'>
    <p>Prueba 1 de ingreso de usuario</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/DbFd0mm.png'>
    <p>Prueba 2 de ingreso de usuario</p>
</div>

Esto funciona como esperábamos, sin embargo las dos pruebas anteriores se realizaron por separado, en el caso en que desde una sola ejecución del programa un usuario ingrese, cierre sesión y después entre otro usuario, se van a ver los datos del anterior usuario. Esto es por que el programa requiere de una actualización no solo de esa parte, también existen otras partes que requieren ser actualizadas y se mencionaran en la siguiente sección.

Por otro lado el uso del servicio **UsuarioService** cobra vital importancia ya que es un servicio que se esta utilizando en varias partes del proyecto, en este caso lo usamos en el componente **login** y el componente **navegacionUsuario** y posiblemente se pueda usar en el componente **perfil** también. Noten que ambos componentes usaron el servicio de forma independiente sin tener que estar comunicados entre ellos, en este caso el uso del atributo **usuarioLogeado** fue crucial para realizar esta independencia, si no existiera este atributo no habría manera de que el componente **navegacionUsuario** sepa que usuario ha ingresado, la única forma de poder saberlo sin el uso del servicio es que el componente **login** le pase el dato del usuario que ingreso al componente **vistaPrincipal** y luego este ultimo se lo pase al componente **navegacionUsuario** y se formaría una dependencia entre componentes muy alta y solo en este caso, imaginen con otras acciones cuanta dependencia mas habría. Es por eso que el uso de servicios se hace especialmente importante en estos casos, siempre hay que encontrar la manera de que estos servicios puedan proporcionar independencia del uso de la información a traves de el y evitar el acoplamiento y envió de información entre componentes que no estar correlacionados.

# Ajustes de nuestro proyecto para el uso de servicios

Como se menciono en la anterior sección, hay partes del proyecto que requieren una actualización, a continuación se mencionan algunas de ellas:
* Cuando dos usuarios entran al sistema a traves del login, es necesario actualizar los datos del componente **navegacionUsuario** para que se vean los datos del ultimo usuario que ingreso.
* Cuando un usuario cierra sesión y se ve nuevamente el Login, este tiene aun los datos que escribió el usuario cuando ingreso, se debería ver justo como cuando se inicia la aplicación
* Cuando ingresamos por segunda vez en una misma ejecución a la vista principal, no se muestra el componente **inicio** y se ve vacía la parte del panel principal.

Vamos a realizar estas respectivas actualizaciones. Empecemos con el componente **login**, vamos a crear un método que se va a llamar **restaurarValores**, dentro de este vamos a dejar todo como cuando el usuario ejecuta la aplicación. 

Esto incluye: 
* Dejar el **JTextField tNombreUsuario** con el placeholder **Nombre Usuario** y el **JPasswordField tClaveUsuario** con el placeholder **calve Usuario**.
* Dejar el **JTextField tNombreUsuario y el JPasswordField** con el borde gris de nuevo.
* Dejar el **JTextField tNombreUsuario y el JPasswordField** con el color de letra gris de nuevo.
* Dejar el **ComboBox cmbTipoUsuario** con la primera selección de nuevo.

```javascript
public void restaurarValores(){
    this.getLoginTemplate().getTNombreUsuario().setText("Nombre Usuario");
    this.getLoginTemplate().getTNombreUsuario().setBorder(
        this.getLoginTemplate().getRecursosService().getBorderInferiorGris()
    );
    this.getLoginTemplate().getTNombreUsuario().setForeground(
        this.getLoginTemplate().getRecursosService().getColorGrisOscuro()
    );
    this.getLoginTemplate().getTClaveUsuario().setText("clave Usuario");
    this.getLoginTemplate().getTClaveUsuario().setBorder(
        this.getLoginTemplate().getRecursosService().getBorderInferiorGris()
    );
    this.getLoginTemplate().getTClaveUsuario().setForeground(
        this.getLoginTemplate().getRecursosService().getColorGrisOscuro()
    );
    this.getLoginTemplate().getCbTipoUsuario().setSelectedIndex(0);
}
```

Ahora cuando cerremos sesión dentro de la **ventana principal** vamos a llamar a este método:

<div align='center'>
    <img  src='https://i.imgur.com/0M6ATxH.png'>
    <p>Llamada del método restaurar valores del componente Login</p>
</div>

Ahora vamos con el componente **VistaPrincipal**, en este caso queremos que cada vez que se inicie sesión siempre se vea el componente **Inicio** dentro de la ventana principal. De la misma manera, vamos a crear un método llamado **restaurarValores** dentro de la clase **VistaPrincipalComponent**:

```javascript
public void restaurarValores(){
}
```

En este caso solo llamaremos al panel **pPrincipal** y le indicaremos que incorpore el componente **Inicio** esto a traves del objeto de su clase compañera **VistaPrincipalTemplate**:

```javascript
public void restaurarValores(){
    this.vistaPrincipalTemplate.getPPrincipal().add(inicioComponent.getInicioTemplate());
}
```

Ahora debemos llamarlo desde el **login** cada vez que se quiera ingresar:

<div align='center'>
    <img  src='https://i.imgur.com/emMxtUj.png'>
    <p>llamada del método restaurarValores de la vistaPrincipal</p>
</div>

Solo nos queda actualizar el componente **NavegaciónUsuario**, dentro de la clase **NavegaciónUsuarioComponent** vamos a crear el método **ActualizarValores**:

```javascript
public void actualizarValores (){
}
```

* Lo primero que debemos hacer es actualizar el usuario que ha ingresado actualmente, para esto llamamos al servicio **UsuarioService** nuevamente:

```javascript
public void actualizarValores (){
    this.usuarioLogeado = sUsuario.getUsuarioLogeado();
}
```

* Debemos remover todo lo que este en el panel **pPrincipal** ya que va a mostrar información nueva:

```javascript
public void actualizarValores (){
    this.usuarioLogeado = sUsuario.getUsuarioLogeado();
    this.navegacionUsuarioTemplate.getPSuperior().removeAll();
}
```

* Una vez se ha retirado todo del panel volvemos a llamar al método **crearJLabels** el cual tomaba los datos del objeto **Usuario** que acaba de actualizarse:
```javascript
public void actualizarValores (){
    this.usuarioLogeado = sUsuario.getUsuarioLogeado();
    this.navegacionUsuarioTemplate.getPSuperior().removeAll();
    this.navegacionUsuarioTemplate.crearJLabels();
}
```

* Finalmente y para asegurarnos de que el cambio se vera llamamos al método **repaint** para que realice la actualización de la ventana.

```javascript
public void actualizarValores (){
    this.usuarioLogeado = sUsuario.getUsuarioLogeado();
    this.navegacionUsuarioTemplate.getPSuperior().removeAll();
    this.navegacionUsuarioTemplate.crearJLabels();
    this.navegacionUsuarioTemplate.repaint();
}
```

Ya esta lista la actualización, solo falta que sea llamado este método, podemos aprovechar el método **restaurarValores** de la vista principal para que de una vez realice la actualización del componente **navegacionUsuario**.

<div align='center'>
    <img  src='https://i.imgur.com/PR22R2M.png'>
    <p>Llamada del método Actualizar Datos del componente NavegacionUsuario</p>
</div>

Ya realizamos los ajustes necesarios y nuestro programa funciona de maravilla. Ahora podemos ingresar varias veces a nuestra ventana principal desde una sola ejecución y con varios usuario y siempre mostrara la información del usuario que acaba de ingresar, también podemos notar que en la vista principal siempre estará incorporado el componente **inicio**. Por otro lado, una vez cerremos sesión el Login de usuario se vera con los valores iniciales a cuando ejecutamos por primera vez la aplicación.

# Resultado

Si has llegado hasta aquí **!Felicidades!** ya has aprendido como crear, cuando utilizar y como funcionan los **Servicios** dentro de nuestra arquitectura. Aprendiste como gestionar un flujo de información externa de forma en que cada componente sera independiente en cuanto a obtener o enviar información a traves del uso de servicios. Aprendiste ademas, las características principales de los **Servicios**. En la proxima clase seguiremos usando servicios para revisar un objeto Gráfico particular, estos son las **JTables**.

# Actividad 

Implementar el uso de **Servicios** en sus proyectos para la gestión de la información externa.