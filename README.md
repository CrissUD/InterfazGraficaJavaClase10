# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 10

## Objetivos

* Examinar las características principales de los servicios y su forma de construcción.
* Reconocer el propósito del uso de Servicios lógicos dentro del proyecto para la obtención de información externa desde cualquier componente gráfico.
* Identificar las distintas funcionalidades que puede tomar un servicio lógico para el manejo de información externa.
* Comprender la forma en que distintas partes del proyecto pueden dar uso de un servicio en común para obtener información.

# Antes de Comenzar

### **Actualización de Imágenes en recursos**
___
Para continuar con el ejercicio deberá actualizar la carpeta **resources/images/perfiles** ya que se han agregado nuevas imágenes. Estas las puede descargar en este mismo repositorio entrando a la carpeta **Clase10** seguido de **resources/images/perfiles**.

### **Actualización de Imágenes en recursos**
___
Se puede observar mediante un modelo estructural general como es la estructura del proyecto hasta el momento:

<div align='center'>
    <img  src='https://i.imgur.com/8q6Uq6C.png'>
    <p>Modelo Estructural del Proyecto</p>
</div>

El anterior es un esquema general de como esta conformado el proyecto, algunos aspectos y detalles se han omitido y solo se muestra lo más relevante en cuanto a la estructura en aspectos como atributos, métodos y relaciones entre clases.

### **Ajustes en el proyecto**
___
* Se va a crear un nuevo paquete desde la carpeta raíz **src** el cual es llamado **archives**, ahi estarán contenidos dos archivos planos que servirán como una simulación de información externa para el propósito de la clase. 

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

### **Ajustes con los servicios**
___
Se van a crear dos paquetes donde se va a separar y contener los servicios de acuerdo a su propósito, estos dos paquétes serán:
* **graphicServices**
* **logicServices**

Adicionalmente se dejan los servicios ya creados **ObjGraficosService y RecursosService** dentro del páquete **graphicServices**:

<div align='center'>
    <img  src='https://i.imgur.com/QJk3ExV.png'>
    <p>Creación de folders para separación de servicios</p>
</div>

### **Recordatorio**
___
Recordando un poco el recorrido, se ha implementado los eventos de Mouse para proporcionar interactividad a varias partes del proyecto, acciones como el arrasate de la ventana, el cambio de color de varios botones y labels, la gestión del contenido y color de los JTetField etc. Paralelamente se vieron algunos temas importantes relacionados con los eventos como: **Representación única para objetos gráficos de una misma Clase** , **Uso de la clase MouseAdapter**, **Discriminación de Clases**, **Efectos hacia otros objetos Gráficos** y el **Uso combinado de varias Interfaces implementadas de eventos**.

# Servicios 

En esta sesión se verá el uso y características de los servicios em general y la implementación de servicios lógicos, para abarcar el tema, la lección esta divida en los siguientes items principales:
* **Explicación general de los servicios**.
* **Servicio lógico contenedor de información externa**.
* **Servicio lógico que recibe información externa**.
* **Ajustes del proyecto para el uso de servicios**.


# Explicación general de los servicios

Antes se ha hablado de los **Componentes Gráficos** y se sabe que estos están conformados por una parte gráfica (**Template**), y una parte lógica (**Component**). Aunque la clase **Component** utiliza lógica detrás para que todas las funcionalidades del componente se puedan cumplir, hay un limite en su responsabilidad, es necesario recordar que esta clase se debe encargar unicamente de soportar la lógica que requiere el componente.

Las aplicaciónes de interfaz Gráfica siempre van a depender de la obtención de información externa que el usuario va a solicitar, ya sea de un servidor web, una base de datos externa, una Api publica etc. Una opción puede ser delegar la obtención de esta información a las clases **Components**, lógicamente puede ser una propuesta valida. Sin embargo, esto puede restar reutilización y modularidad dentro del proyecto. Esta información externa tiene ciertos datos que seguramente van a ser solicitados por varias partes del proyecto y si en cada componente que se necesite dicha información se va a consumir un mismo servicio web externo existirá una acumulación grande de peticiones lo que podría generar un mal rendimiento.

Otra forma para obtener esta información sin afectar el rendimiento es obtener la información externa una vez desde cualquier componente y empezar a pasar dicha información entre componentes, sin embargo, esta practica hará que el proyecto esté muy acoplado y exista una dependencia alta entre componentes gráficos.

Una solución mas coherente es hacer uso de servicios que van a tener un propósito bien definido y que puede encapsular alguna funcionalidad o información externa que será requerida por varias partes del proyecto. De esta manera el servicio hará solo las peticiones necesarias a las entidades externas y ademas si algún componente necesita de esta información puede solicitarla al servicio haciendo que los componentes sean independientes entre si y no exista un acoplamiento masivo.

Estas clases entonces se encargan principalmente de contener la información que se va a obtener externamente. Pueden existir varios tipos de servicios:
* **Servicios Lógicos:** Que se encargan de contener información externa y que será solicitada por varas partes del proyecto. Siempre serán solicitados por las clases **Component** de los componentes.
* **Servicios Gráficos:** Que contiene una funcionalidad o información especifica que está relacionada con la creación de objetos gráficos. Siempre serán solicitados por las clases **Template**.

Independientemente de su clasificación la estructura de los servicios es similar, es posible crear servicios **Singleton** o en su defecto realizar varios objetos de un servicio (Esto en ocasiones muy especiales).

Se han visto a lo largo del curso el uso de **Servicios Gráficos**, específicamente:
* El servicio Gráfico **ObjGraficosService** encargado de encapsular la construcción de objetos gráficos.
* El servicio Gráfico **RecursosService** encargado de la creación responsable de objetos decoradores que puedan ser solicitados por varios template.

En los anteriores servicios se evidencian los dos propósitos principales de los servicios:

* **Contener Funcionalidad común**: El servicio **ObjGraficosService** tiene encapsulada una funcionalidad que es usada por la mayoría de clases Template que se han creado. Este servicio contiene una serie de métodos encargados de la construcción de objetos gráficos y que ayuda a las clases **Template** con el proceso de **creación de objetos gráficos**. Esta funcionalidad es común y general por lo que permite que se use en varias partes del proyecto e incluso en varios proyectos.
* **Contener Información común**: El servicio **RecursosService** de alguna manera se esta conteniendo información, no es información externa evidentemente ni tampoco información compuesta de datos comúnes. Pero desde una perspectiva general, este servicio crea objetos decoradores (información) que son solicitados desde varias partes de la aplicación.

El factor más importante del uso de Servicios esta en su propósito de contener algo que será solicitado por varias partes del proyecto, evitando así el acoplamiento extremo entre componentes gráficos. Por ejemplo si el servicio **RecursosService** no existiera pero se quisiera controlar la creación de objetos decoradores como colores, fuentes, bordes etc. La única forma de realizar esto es creándolos todos desde la clase **App** y empezar a repartir estos objetos mediante los constructores de los componentes lo que generaría un altísimo acoplamiento entre clases.

# Servicio lógico contenedor de información externa

Varias veces la información externa es necesaria solo para propósitos visuales, es decir información que será mostrada pero que no necesariamente está contenida en algún servidor web o una base de datos, simplemente es información que se quiere mostrar en la interfaz gráfica, pero que no es tan importante para guardarse en una base de datos o un servidor web externo.

En este caso se va a contener información relacionada con las acciónes que se muestran en el componente **inicio**. Como recordatorio estas acciónes se muestran a través de la **reutilización de componentes gráficos** pero había una cantidad considerable de código debido a esta reutilización, esta vez se va a recibir la información de las acciones a través de un archivo plano (Simulación de información externa) y se contendrá en un servicio para reducir el código de la clase.

<div align='center'>
    <img  src='https://i.imgur.com/mZFvPJ4.png'>
    <p>Acciones dentro del componente Inicio.</p>
</div>

Primero se analiza la estructura de los archivos plano acciones:
<div align='center'>
    <img  src='https://i.imgur.com/wjSAbYB.png'>
    <p>Estructura de archivo plano, acciones</p>
</div>

Pueden notar que el archivo plano contiene cierta información sobre cada acción y se debe resaltar que cada parte de la información por acción esta separada por una coma **(,)** y cada acción esta separada por un salto de linea. Entre sus partes están:
* **Titulo de Acción**.
* **Descripción de Acción**.
* **Dirección de Imagen**.

Ahora se crea el servicio **AccionService**, esta clase se crea dentro del paquete **logicServices**:

<div align='center'>
    <img  src='https://i.imgur.com/Rpt4adF.png'>
    <p>Creación de servicio AccionService</p>
</div>

Para tener un control de la creación del objeto del servicio se va a implementar el patron **Singleton** de igual manera, sin embargo, se deja una pequeña variación y es que el constructor va a quedar publico. Esto debido a que puede que  en algún momento una parte del proyecto va a necesitar un objeto del servicio en el cual no se le haya cargado ninguna información previamente y con esto tendrá la posibilidad de crear una ejemplificación del servicio diferente para sus propósitos individuales, por otro lado si necesita información que puede ser compartida entre varios componentes de seguro preferirá obtener el objeto del servicio que están usando los demás a través del singleton.

* Primero se declará un objeto de si mismo, recuerde que debe ser un atributo tipo **static**.
```javascript
    private static AccionService servicio;
```

* Se crea el método estático para la creación única del servicio: 

```javascript
public static AccionService getService() {
  if(servicio == null)
    servicio = new AccionService();
  return servicio;
}
```

* Como se explico el constructor se deja publico (es decir en su forma normal).

Antes de continuar con el servicio se procede a crear otro paquete desde la carpeta raíz **src**, la cual será llamada **models**, adentro se crea una clase llamada **Accion** y será la representación en objeto de las acciones. 

<div align='center'>
    <img  src='https://i.imgur.com/usKErN0.png'>
    <p>Creación de paquete models y la clase Accion.java</p>
</div>

* Dentro de esta clase se declaran los atributos:
```javascript
// Dentro de la clase Accion
private String nombreAccion;
private String descripcionAccion;
private ImageIcon imagenAccion;
```
* Ahora se generan los métodos **get y set** correspondientes a sus atributos:

```javascript
public String getNombreAccion () { return nombreAccion; }

public String getDescripcionAccion () { return descripcionAccion; } 

public ImageIcon getImagenAccion () { return imagenAccion; }

public void setNombreAccion (String nombreAccion){
  this.nombreAccion = nombreAccion;
}

public void setDescripcionAccion (String descripcionAccion){
  this.descripcionAccion = descripcionAccion;
}

public void setImagenAccion (ImageIcon imagenAccion){
  this.imagenAccion = imagenAccion;
}
```

***Nota:** El anterior es un esquema general de una clase del modelo. Es bueno manejar la información por medio de objetos que encapsulen la información y es un pilar fundamental en la programación orientada a objetos*

Dentro del servicio **AccionService**, este servicio va a contener un atributo que sera una lista de todas las acciones necesarias dentro del proyecto, para hacer esto es necesario un objeto tipo **arrayList**, este es un tipo de arreglo que tiene ciertas ventajas, por ejemplo puede contener objetos y ademas no tiene un tamaño fijo por lo que puede agregarse o quitarse objetos dentro de la lista en tiempo de ejecución:

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

***Nota:** Entre los signos **<>** esta el tipo de objetos que va a contener, en este caso el arreglo **acciones** va a contener objetos de tipo **Accion**, aunque el arreglo es dinámico, al ser un arreglo es preferible que contenga un solo tipo de dato.*

Como este servicio se va a encargar de obtener la información externa, es necesario crear un método encargado de recibir la información del archivo plano. Muchas veces esta información que no proviene de algún servidor o base de datos externa esta contenida en archivos planos y es una ayuda para la encapsulación de información que puede cambiar con el tiempo y que solo tiene propósitos visuales en la aplicación.

```javascript
public void cargarDatos() {
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
  } catch(Exception e) {
    e.printStackTrace();
  }
}
```
El anterior código se encarga de leer la información contenida en el archivo plano **acciones.txt**, una vez se ha leído una linea dentro del archivo se crea un objeto de la acción y se inserta en la lista de acciones. La explicación de este método no es el fin de esta clase pero se va a explicar a grandes rasgos su función en la siguiente imágen.

<div align='center'>
    <img  src='https://i.imgur.com/yplcQvL.png'>
    <p>Explicación de código de obtención de información del archivo plano</p>
</div>

Para que la información pueda ser cargada una vez se obtenga el servicio se debe llamar este método desde el constructor:

```javascript
public AccionService() {
  acciones = new ArrayList<Accion>();
  cargarDatos();
}
```

Ahora se crea un método encargado de retornar una accion cuando algún componente gráfico lo necesite:

* El método debe retornar un objeto tipo **Accion** y debe recibir por parámetro un entero que representa la posición en el arreglo de la accion solicitada.

```javascript
public Accion devolverAccion(int posicion){
}
```

* Se va a hacer la petición mediante un **try/catch**, esto para el control de errores. Dentro del Try se va a intentar retornar un objeto **Accion**, en caso de que se genere un error en el momento de retornar una acción se puede gestionar el error a través del catch.
```javascript
public Accion devolverAccion(int posicion){
  try{
  }
  catch(Exception e){
  }
}
```
* Dentro del Try se retorna la acción dentro de la lista que se pida mediante la posición enviada como argumento, en dado caso en que la posición no exista dentro del arreglo, se va a retornar un null.

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

El servicio esta listo y puede usarse en varias partes del proyecto, por ahora será usado en un lugar especifico, el componente **Inicio**. Dentro de la clase **InicioComponent** y lo primero que se debe hacer es obtener el servicio:

* **Declaración:**
```javascript
private AccionService sAccion;
```

* **Obtención del Servicio:**
```javascript
// Dentro del constructor
sAccion = AccionService.getService();
```
***Nota:** Es importante aclarar que la obtención del servicio debe hacerse antes de cargar la parte gráfica del componente ya que se van a pedir datos al servicio para ser mostrados en pantalla y si se muestra primero el componente antes de obtener el servicio esta información no existirá y saldrá un error.*

Una vez obtenido el servicio de acciones, se crea un método que se comunique con el servicio y que de igual forma retorne una **Accion** que obtiene del Servicio:

* De igual forma retornara un objeto tipo **Accion** y recibe por parámetro la posicion de la acción en el arreglo dinámico.

```javascript
public Accion obtenerAccion(int numeroAccion){

}
```

* Ahora se debe llamar al método creado en el servicio **AccionService** pasándole como argumento la posicion de la acción que se recibió como parámetro:

```javascript
public Accion obtenerAccion(int numeroAccion){
  return sAccion.devolverAccion(numeroAccion);
}
```

Ahora, en la clase **InicioTemplate** se puede obercar como esta escrito el código del método **crearContenidoPAcciones()**, en el se establece la creación de cada componente **Accion** de forma manual.

<div align='center'>
    <img  src='https://i.imgur.com/PUtZqXf.png'>
    <p>Creación manual del Componente Gráfico Accion</p>
</div>

Ahora que se tiene la información de todas las acciones, es posible automatizar la creación del componente gráfico **Acción** varias veces (Aprovechando también la reutilización de componentes). Se procede a borrar la creación manual de todas las acciones y solo se deja la creación del título intacta:

```javascript
public void crearContenidoPAcciones() {
  this.lAcciones = sObjGraficos.construirJLabel(
    "Nuestros Servicios", 
    10, 10, 160, 30, 
    null, 
    sRecursos.getColorPrincipal(), 
    null, 
    sRecursos.getFontTitulo(), 
    "c"
  );
  this.pAcciones.add(lAcciones);
}
```

Ahora se va a crear un **Contador** esta variable va a servir como la posición a buscar dentro del arreglo de acciones que contiene el servicio **AccionService**, también se crea otro entero que será responsable de la posición en las filas de los componentes de Accion:

```javascript
public void crearContenidoPAcciones() {
  // Creación del Titulo
  ...

  int numeroAccion = 0, fila = 0;
}
```

Ahora se crea un objeto de tipo **Accion** (que se encuentra en el paquete **Models**) y se iguala con la llamada del método que esta en la clase **Component** que a su vez va a llamar al método que se encuentra en el servicio **ActionService**:

```javascript
public void crearContenidoPAcciones(){
  // Creación del Titulo
  ...
  
  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
}
```

En el anterior código se ha llamado al método **obtenerAccion** de la clase **InicioComponent** y como argumento se pasa el **Contador** que vale 0 inicialmente, esto quiere decir que se ha obtenido desde el servicio **AccionService** la **acción** que se encuentra en la primera posición de la lista **acciones**.

Se crea ahora un ciclo **while** y la condición para que el ciclo continue es que al llamar al servicio para obtener una accion este no retorne un dato tipo **nulo** o en otras palabras que una vez ya no existan más acciones en el arreglo el ciclo se termine.

```javascript
public void crearContenidoPAcciones() {
  // Creación del Titulo
  ...
  
  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
  while(accion != null) {
    
  }
}
```

Como ya se recibió la primera accion antes de entrar al ciclo la condición se cumple y puede entrar, dentro del ciclo se crea el componente gráfico **Accion** y como se sabe, se debe llamar a su clase **AccionComponent** para acceder a este.

* Primero se ejemplifica la clase **Component**:
```javascript
public void crearContenidoPAcciones() {
  // Creación del Titulo
  ...
  
  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
  while(accion != null) {
    AccionTemplate pAccion = new AccionComponent();
  }
}
```

***Nota:** Puede observar que se crea un objeto tipo Template del Componente Accion pero se esta igualando a la ejemplificación de la clase Component, esto claramente va a traer error de compatibilidad de objetos, sin embargo, esto se hace por que de una vez en la ejemplificación se va a traer la clase template para asegurar que la igualdad sea cierta.*

Recordando, la clase **AccionComponent** pide ciertas cosas por parámetros como **una imagen, un título y un párrafo de descripción**. 

<div align='center'>
    <img  src='https://i.imgur.com/QhRZQGd.png'>
    <p>Parámetros exigidos por el componente Acción</p>
</div>

Ahora que se tiene toda esta información encapsulada en un objeto, conviene cambiar un poco la estructura del componente reutilizable para que solo exija un objeto de tipo Accion como parámetro:

<div align='center'>
    <img  src='https://i.imgur.com/1eQUfxK.png'>
    <p>Cambio en la petición de parámetros dentro de la clase AccionComponent</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/p4qMoQc.png'>
    <p>Cambio en la petición de parámetros y ajusto en la clase AccionTemplate</p>
</div>

Una vez realizadas estas modificaciones se enviá como argumento a la acción que se tiene actualmente:

```javascript
public void crearContenidoPAcciones() {
  // Creación del Titulo
  ...
  
  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
  while(accion != null) {
    AccionTemplate pAccion = new AccionComponent(accion);
  }
}
```

Ahora el editor o IDE muy seguramente debe estar sacando error y esto es por que la igualdad **Clase Template = Clase Component** no es verdadera, se debe llamar a la clase **Template** del componente para que esta igualdad se cumpla:

```javascript
public void crearContenidoPAcciones() {
  // Creación del Titulo
  ...
  
  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
  while(accion != null) {
    AccionTemplate pAccion = 
      new AccionComponent(accion).getAccionTemplate();
  }
}
```

El componente **Accion** ya ha sido ejemplificado de manera exitosa, sin embargo, es necesario recordar que se esta realizando una **reutilización por posicionamiento** esto quiere decir que se debe dar la posición a cada componente acción que se llame. En este caso se sabe que se tienen 6 acciones, sin embargo esto podría cambiar con el tiempo y se podrían agregar mas acciones en el archivo plano, para generar un posicionamiento general se realiza el siguiente calculo basado en el modulo, se muestra el código y a continuación se explica:

```javascript
public void crearContenidoPAcciones(){
  // Creación del Titulo
  ...
  

  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
  while (accion != null) {
    AccionTemplate pAccion = 
      new AccionComponent(accion).getAccionTemplate();
    pAccion.setLocation(
      15 + ((pAccion.getWidth() + 15) * (numeroAccion % 3)),
      50 + ((pAccion.getHeight() + 15) * fila)
    );
    if (numeroAccion % 3 == 2) fila++;
  }
}
```

Para explicar el calculo anteriormente realizado se va a explorar la posición por cada eje:
* **Eje X**
  * Se tiene un numero inicial 15 que será constante, esto debido a que las acciones de la primera columna siempre van a empezar 15px sobre el eje X.
  * Los siguientes criterios para el posicionamiento son el **ancho del componente accion** y una **distancia de 15px entre cada componente**. Esto quiere decir que los componentes de la columna 1 siempre están en la coordenada 15px, mientras que los de la segunda columna deberán sumar a esos 15px los 250px del ancho mas 15px para una distancia entre componentes.
  * El criterio mas importante es el **modulo con 3**, se sabe que cada fila va a tener un total de 3 columnas, el modulo con 3 indicará el numero de columna en que se posicionará cada componente (cada columna contada desde 0 hasta 2), el modulo es clave ya que va a generar un limite haciendo que los resultados puedan variar solamente desde 0 hasta 2, esto quiere decir que:
    * Si el numero de acción esta en 0, se realiza la operación 0 % 3 lo que arroja un resultado de 0 indicando que la primera acción se ubica en la columna 0.
    * Si el numero de acción esta en 2, se realiza la operación 2 % 3 lo que arroja un resultado de 2 indicando que la tercera acción se ubica en la columna 2.
    * Si el numero de acción esta en 3, se realiza la operación 3 % 3 lo que arroja un resultado de 0 indicando que la cuarta acción se ubica en la columna 0.
  * Como el modulo va a retornar números de 0 a 2 este resultado es multiplicado con el ancho del componente y la distancia para generar su posición final en el eje X de esta forma: 
    * La acción 1 (cuyo numero de Accion es 0) Genera un modulo en 0, lo que hace que su posición final quede en 15px.
    * La acción 3 (cuyo numero de Accion es 2) genera un modulo en 2, lo que hace que su posición final quede en **15px (constante) + ((250px (ancho) + 15px (distancia)) * 2 (modulo)) = 545px**.
    * La acción 4 (cuyo numero de Accion es 3) Genera un modulo en 0, lo que hace que su posición final quede en 15px.

* **eje Y**
  * Se tiene un numero inicial 50 que será constante, esto debido a que las acciones de la primera fila siempre van a empezar 50px sobre el eje Y.
  * Los siguientes criterios para posicionamiento son **la altura del componente** y una **Distancia entre componentes** y se basa en el mismo principio explicado en el eje X.
  * El principal criterio es el entero llamado **fila**, este inicia en 0 y puede aumentar su valor dependiendo de cuantas acciones existan.
  * La fila va a depender nuevamente del **modulo con 3** ya que gracias a este se establece el numero de columna donde se posiciona la acción actual, y con esto se crea un condicional donde se indica que si la columna actual es la ultima (2) se aumente el valor de la fila para que se posicionen los siguientes componentes de accion debajo.

Para finalizar el ciclo de manera correcta es necesario:
* Agregar el componente al panel.
* Aumentar el Contador.
* Llamar al siguiente objeto de acción en la lista.

```javascript
public void crearContenidoPAcciones(){
  // Creación del Titulo
  ...
  

  int numeroAccion = 0, fila = 0;
  Accion accion = inicioComponent.obtenerAccion(numeroAccion);
  while (accion != null) {
    AccionTemplate pAccion = 
      new AccionComponent(accion).getAccionTemplate();
    pAccion.setLocation(
      15 + ((pAccion.getWidth() + 15) * (numeroAccion % 3)),
      50 + ((pAccion.getHeight() + 15) * fila)
    );
    if (numeroAccion % 3 == 2) fila++;
    this.pAcciones.add(pAccion);
    numeroAccion++;
    accion = inicioComponent.obtenerAccion(numeroAccion);
  }
}
```

Las Acciones están listas y se muestran correctamente, podemos ejecutar la aplicación y verificar:

<div align='center'>
    <img  src='https://i.imgur.com/6LcW9rb.png'>
    <p>Componentes Accion creados de forma automática.</p>
</div>

Ya se realizó la automatización de la creación de **componentes gráficos reutilizables** y es un complemento de lo que se había visto previamente sobre ese tema. Sin embargo, vale la pena aclarar que el fin del servicio **AccionService** no es solamente poder realizar esta automatización, como se explico previamente este servicio contiene la información de estas acciónes que se pueden usar en varias partes del proyecto. Aprovechando el concepto de reutilización de componentes podría de alguna forma crearse el listado de dichas acciones en otra parte del proyecto y ya no habría necesidad de solicitar los datos nuevamente a la entidad externa ya que esta esta contenida en el servicio ni tampoco realizar una comunicación innecesaria entre componentes para el traspaso de información. 

Esta acción no se realizará en el momento, pero para explicar lo anterior referente al uso compartido de servicios lógicos se va a ver en la siguiente sección el uso de otro servicio en donde si conviene realizar lo anterior descrito ya que se necesita en varias partes del proyecto.

***Nota:** Como ya se realizó la automatización de los paneles de accion, es posible borrar las imágenes que se habían creado en la clase **InicioTemplate** y que contenía las imágenes de las acciones ya que no se estan usando mas, de esta forma el método **crearObjetosDecoradores()** queda asi:*

<div align='center'>
    <img  src='https://i.imgur.com/1y42Aj6.png'>
    <p>Método crearObjetosDecoradores sin las imágenes de acción</p>
</div>

# Servicio lógicos que recibe información externa

Este tipo de servicios no contienen la información externa directamente, mas bien deben obtenerla desde algún sistema ajeno primero, estos pueden ser algún servidor externo, una base de datos, una Api publica etc. En este caso se va a manejar la información de los usuarios que ya están registrados en el sistema y estos serán los únicos que pueden entrar. Esta información esta contenida en el archivo plano **usuarios.txt**.

<div align='center'>
    <img  src='https://i.imgur.com/6UrTJco.png'>
    <p>Información contenida de los usuarios</p>
</div>


La información incluye el **Nombre del usuario, clave del usuario, tipo usuario e imagen usuario**. Para aclarar normalmente las contraseñas se guardan en las bases de datos de forma encriptada, sin embargo, estos son temas del Backend que este curso no va a tocar,  en este caso solo se simula un ejemplo de información externa.

Similarmente a la gestión de las acciones se va a crear un objeto en el modelo que represente el objeto de los usuarios:

<div align='center'>
    <img  src='https://i.imgur.com/GX0KBi2.png'>
    <p>Creación de clase Usuario dentro del paquete Models</p>
</div>

Dentro de la clase **Usuario** se declaran los atributos de un usuario que ya fueron descritos y ademas se crean sus respectivos métodos **set y get**.

```javascript
public class Usuario {
  private String nombreUsuario;
  private String claveUsuario;
  private String tipoUsuario;
  private ImageIcon imagenUsuario;

  public String getNombreUsuario() { return nombreUsuario; }

  public String getClaveUsuario() { return claveUsuario; }

  public String getTipoUsuario() { return tipoUsuario; }

  public ImageIcon getImagenUsuario() { return imagenUsuario; }

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

Ahora se va a simular un controlador de información externa de forma local, (esto por motivos de ejemplo), normalmente la aplicación frontend se comunicara con Apis, servicios o bases de datos externas.

Se crea un nuevo paquete desde la carpeta raíz **src** la cual será llamada **logic** y dentro se crea una clase llamada **ControlUsuarios**, esta será la clase encargada de obtener, contener y gestionar la información.

<div align='center'>
    <img  src='https://i.imgur.com/4VzVvr1.png'>
    <p>Creación del paquete Logic y su controlador.</p>
</div>

Como el controlador va a contener la información, esta va a obtenerse a través del archivo plano:

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
public void cargarDatos() {
  File archivo = null;
  FileReader fr = null;
  BufferedReader br = null;
  try {
    archivo = new File ("Clase10/src/archives/usuarios.txt");
    fr = new FileReader (archivo);
    br = new BufferedReader(fr);

    String linea;
    while((linea=br.readLine())!=null) {
      String[] atributos = linea.split(",");
      Usuario usuario = new Usuario();
      usuario.setNombreUsuario(atributos[0]);
      usuario.setClaveUsuario(atributos[1]);
      usuario.setTipoUsuario(atributos[2]);
      usuario.setImagenUsuario(new ImageIcon(atributos[3]));
      usuarios.add(usuario);
    }
    fr.close(); 
  } catch(Exception e) {
    e.printStackTrace();
  }
}
```

* Se llama al método desde el constructor:
```javascript
// Dentro del Constructor
cargarDatos();
```

* Se crea un método que recibe el nombre de un usuario y retorna un objeto de tipo Usuario en caso de que exista un usuario en la lista que coincida con el nombre:
* **Declaración del método**
```javascript
public Usuario devolverUsuario(String nombreUsuario) {

}
```

* **Se crea un ciclo que recorra la lista:**
```javascript
public Usuario devolverUsuario(String nombreUsuario) {
  for (Usuario usuario : usuarios) {
  
  }
}
```
***Nota:** En el anterior ciclo se esta recorriendo cada posición del arrayList, pero en vez de usar un **Contador** que pase por cada posición de la lista, se esta usando directamente una variable objeto de tipo **Usuario** (creado en el modelo), que se va a convertir en el objeto que esta en la posición donde se esta revisando actualmente, este ciclo es normalmente conocido como **for each** o **for of**.*
* **Se realiza la validación:**
```javascript
public Usuario devolverUsuario(String nombreUsuario) {
  for (Usuario usuario : usuarios) {
    if (usuario.getNombreUsuario().equals(nombreUsuario))
      return usuario;
  }
  return null;
}
```
En este caso se pregunta si el objeto actual que se esta revisando en la lista tiene el nombre que coincide con el dato recibido como parámetro entonces retorne dicho objeto, de lo contrario retorne un dato nulo.

Ahora se crea el servicio **UsuarioService**

<div align='center'>
    <img  src='https://i.imgur.com/EksE4Ae.png'>
    <p>Creación del servicio UsuarioService</p>
</div>

Se crea la estructura básica del servicio para ser obtenido:
* Declaración de la referencia estática a si mismo: 
```javascript
// Dentro del Servicio UsuarioService
private static UsuarioService servicio;
```

* Método estático para el control de una sola ejemplificación:
```javascript
public static UsuarioService getService() {
  if(servicio == null)
    servicio = new UsuarioService();
  return servicio;
}
```

* El constructor se deja publico como ya se explico en la anterior sección.

* Este servicio va contener dos atributos que serán de vital importancia, un atributo será el objeto del controlador para que exista una comunicación hacia la entidad externa, el otro atributo es de tipo Usuario y se encargará de guardar en memoria que usuario ha iniciado sesión actualmente.

* **Declaración:**
```javascript
// Dentro del Servicio UsuarioService
private ControlUsuarios cUsuario;
private Usuario usuarioConectado;
```

* **Ejemplificación**
```javascript
// Dentro del Constructor
cUsuario = new ControlUsuarios();
```

## Validación de usuarios

Para realizar la validación se va a verificar cuatro cosas:
* Que todos los campos se hayan diligenciado.
* Que el nombre del usuario coincida con algún registro.
* Que la contraseña del usuario coincida con el nombre del usuario registrado.
* Que el tipo del usuario coincida con el nombre y clave del usuario registrado.

Para que una persona pueda ingresar a la aplicación debe proporcionar esos tres datos y estos deben estar guardados en el lugar de persistencia de datos (En este ejercicio el archivo plano), ademas de eso los 3 datos deben coincidir para un mismo usuario, de lo contrario no podrá entrar. 
Sin embargo esta gestión de verificación del usuario no la debe realizar la parte Frontend de un proyecto, normalmente esto lo realiza el Backend y el proyecto del cliente solo recibe la respuesta de la validación una vez enviá los datos que el usuario ha proporcionado.

Para simular esto, se va a realizar la respectiva validación dentro de la clase **ControlUsuarios** que recuerde, se supone es una clase externa del proyecto y solo se usa por motivos de simulación de una aplicación real.

Dentro de esta clase se va a crear el método **VerificarUsuario** el cual va a recibir por parámetros 
* **String nombreUsuario**
* **String claveUsuario** 
* **String tipoUsuario**

Y va a retornar un **Boolean** que indicará si la validación fue correcta o no.
```javascript
public boolean verificarUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario) {

}
```

Dentro de este método se va a recorrer el arreglo de usuarios llamado **usuarios** esto mediante el siguiente ciclo:

```javascript
public boolean verificarUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario) {
  for(Usuario usuario : usuarios) {
    
  }
}
```
Dentro de ese ciclo se realiza la respectiva validación:
```javascript
public boolean verificarUsuario(String nombreUsuario, String claveUsuario, String tipoUsuario) {
  for(Usuario usuario : usuarios) {
    if(usuario.getNombreUsuario().equals(nombreUsuario))
      if(usuario.getClaveUsuario().equals(claveUsuario))
        if(usuario.getTipoUsuario().equals(tipoUsuario))
          return true;
  }
  return false;
}
```

En la anterior validación se indica que si en algún momento del recorrido del arreglo encuentra un objeto **Usuario** que cumple con la igualdad de los 3 datos dados por el usuario, se va a retornar un **True** indicando que se ha realizado la validación satisfactoriamente, en caso de no encontrar ningún objeto **Usuario** que cumpla con los requisitos, saldrá del ciclo y retornara un **False** indicando que el usuario no existe en el sistema.

Dentro de la clase del servicio **UsuarioService** (El cual ya es parte del proyecto) se crea un nuevo método llamado **verificarDatosUsuario** el cual recibirá de nuevo los tres datos que el usuario proporcionará y de igual forma retornará un **boolean**.

```javascript
public boolean verificarDatosUsuario(
  String nombreUsuario, String claveUsuario, String tipoUsuario
) {

}
```

Dentro se realiza el llamado del método que validará los datos dentro del control y en caso de que la validación sea efectiva se realiza una acción de suma importancia.

```javascript
public boolean verificarDatosUsuario(
  String nombreUsuario, String claveUsuario, String tipoUsuario
) {
  if (cUsuario.verificarUsuario(nombreUsuario, claveUsuario, tipoUsuario)) {
    this.usuarioConectado = cUsuario.devolverUsuario(nombreUsuario);
    return true;
  }
  return false;
}
```

Noten que dentro del **condicional** se ha llamado al método del Controlador encargado de la validación, en caso de que este retorne un **True** va a entrar dentro de las instrucciones dadas por el condicional, en este caso será que si el usuario efectivamente proporciono los datos de un usuario registrado va a realizar otra petición a la entidad externa pidiendo esta vez el objeto del usuario que coincida con el nombre proporcionado e igualandolo al atributo **usuarioConectado**.
En caso de que la verificación haya retornado un **False** este método retornará un **False**.

Por ultimo y para que el atributo **usuarioConectado** tenga validez en el futuro se crea un método que retorne este atributo:
```javascript
public Usuario getUsuarioConectado() {
  return this.usuarioConectado;
}
```

El Servicio esta listo para realizar la verificación ahora se debe configurar el componente **Login** para hacer uso de este servicio. Lo primero que se va a realizar es la obtención del servicio **UsuarioService** desde el componente, específicamente desde la clase **LoginComponent**. 

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
***Nota:** Es importante aclarar que la obtención del servicio debe hacerse antes de cargar la parte gráfica del componente ya que se van a pedir datos al servicio para ser mostrados en pantalla y si se muestra primero el componente antes de obtener el servicio esta información no existirá y saldrá un error.*

Recordando, una vez el usuario oprime el botón de entrar dentro del Login el usuario podia ver un mensaje emergente que le indicaba los datos que había proporcionado y después entraba a la ventana principal.

<div align='center'>
    <img  src='https://i.imgur.com/83tkod4.png'>
    <p>Evento una vez se oprime el botón entrar del Login</p>
</div>

Esta funcionalidad debe cambiar, ya no se van a mostrar esos datos, eso solo se hizo como una prueba para ver como se realizaba el recibimiento de información desde la parte gráfica. Para empezar se va a cambiar el nombre del método **mostrarDatosUsuario** por **enviarDatosUsuario**, y se va a quitar el llamado del método entrar, ya que ahora solo podrá entrar en el caso de que el usuario este registrado.

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

Dentro del método enviarDatosUsuario se va a dejar unicamente la obtención de los datos de los dos campos de texto y del combobox, el resto no será necesario.

```javascript
public void enviarDatosUsuario() {
  String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
  String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
  String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
}
```

Ahora se va a realizar una validación inicial donde se comprueba que el usuario haya proporcionado todos los datos, en caso contrario que muestre un mensaje de advertencia:
```javascript
public void enviarDatosUsuario() {
  String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
  String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
  String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
  if(!nombreUsuario.isBlank() && !claveUsuario.isBlank()) {
  
  } else
    JOptionPane.showMessageDialog(null, "No puede dejar un campo vacio", "Advertencia", 2);
}
```

Si el usuario proporcionó todos los datos, se van a enviar al servicio **UsuarioService** para realizar la respectiva validación, en caso de ser verificado satisfactoriamente mostrará un mensaje indicando el ingreso exitoso y entrará a la vista principal, en el caso contrario mostrará un mensaje indicando que algo ha salido mal.

```javascript
public void enviarDatosUsuario() {
  String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
  String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
  String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
  if(!nombreUsuario.isBlank() && !claveUsuario.isBlank()) {
    if (sUsuario.verificarDatosUsuario(nombreUsuario, claveUsuario, tipoUsuario)) {
      JOptionPane.showMessageDialog(null, "Ingreso Exitoso", "Advertencia", 1);
      entrar();
    } else
      JOptionPane.showMessageDialog(null, "Algo quedo mal", "Advertencia", 2);
  } else
    JOptionPane.showMessageDialog(null, "No puede dejar un campo vacio", "Advertencia", 2);
}
```
Una vez se ejecuta el programa es posible verificar su funcionamiento:

<div align='center'>
    <img  src='https://i.imgur.com/lc3L4ko.png'>
    <p>Caso exitoso de ingreso de usuario</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/k3kSlRi.png'>
    <p>Caso fallido de ingreso de usuario</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/aFORSjF.png'>
    <p>Caso fallido de ingreso de usuario</p>
</div>

Ahora se quiere que una vez el usuario haya entrado exitosamente, los datos de este como el nombre del usuario y la foto del perfil puedan observarse en la navegación del usuario, por el momento solo se esta utilizado una imagen y un texto de forma estática:

<div align='center'>
    <img  src='https://i.imgur.com/gdH5Mdz.png'>
    <p>Información estática del usuario</p>
</div>

Para cambiar esta información se debe llamar al servicio **UsuarioService** desde el componente **NavegaciónUsuario** para conseguir los datos del usuario que se ha conectado. De hecho toda la estructura tanto en el controlador como en el servicio esta lista para realizar esta acción.

En este caso se debe llamar al método **getUsuarioConectado** que retornará el objeto del usuario que actualmente ha iniciado sesión, recuerde que este atributo se estableció en el momento en que la verificación del usuario fue exitosa:

<div align='center'>
    <img  src='https://i.imgur.com/P9rP0VV.png'>
    <p>Momento en el que se le dio valor al atributo usuarioConectado</p>
</div>

Mas adelante se realiza una reflexión de por que se realiza esta metodología, por ahora se va a hacer uso del servicio.

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
***Nota:** Es importante aclarar que la obtención del servicio debe hacerse antes de cargar la parte gráfica del componente ya que se van a pedir datos al servicio para ser mostrados en pantalla y si se muestra primero el componente antes de obtener el servicio esta información no existirá y saldrá un error.*

Se crea un atributo que se llamara **usuarioConectado** y sera de tipo **Usuario**, una vez declarado, se usa el servicio **UsuarioService** para obtener el objeto del usuario conectado. 

* **Declaración:**
```javascript
// Dentro de la clase NavegacionUsuarioComponent
private Usuario usuarioConectado;
```

* **Obtención del objeto del usuario que ha ingresado:**
```javascript
// Dentro del constructor
this.usuarioConectado = sUsuario.getUsuarioConectado();
```
Finalmente vamos a crear su método **get**, esto para que pueda ser obtenido por su clase gráfica **Template**:

```javascript
public Usuario getUsuario(){
  return this.usuarioConectado;
}
```

Finalmente dentro de la clase **NavegacióUsuarioTemplate** se realizan unos pequeños cambios con los label que muestran la imagen y nombre del usuario:

<div align='center'>
    <img  src='https://i.imgur.com/bqJKksQ.png'>
    <p>Cambios en los labels dentro del método crearJLabels de la clase NavegacionUsuarioTemplate</p>
</div>

Noten que en el caso del Label **lNombreUsuario** Ahora se enviá como argumento en el texto al nombre del usuario que se obtiene a través de la clase **Component**. Lo mismo pasa con la imagen, cuando se va a redimensionar se llama a la imagen del objeto del usuario que se obtiene a través de la clase **Component**.

Se realizan algunas pruebas a ver que sucede:

<div align='center'>
    <img  src='https://i.imgur.com/YSyuQHW.png'>
    <p>Prueba 1 de ingreso de usuario</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/pMWxj6x.png'>
    <p>Prueba 2 de ingreso de usuario</p>
</div>

Esto funciona como se esperaba, sin embargo, las dos pruebas anteriores se realizaron por separado (en diferentes ejecuciones), en el caso en que desde una sola ejecución del programa un usuario ingrese, cierre sesión y después entre otro usuario, se van a ver los datos del anterior usuario. Esto es por que el programa requiere de una actualización no solo de este componente, también existen otras partes que requieren ser actualizadas y se mencionaran en la siguiente sección.

Por otro lado el uso del servicio **UsuarioService** cobra vital importancia ya que es un servicio que se esta utilizando en varias partes del proyecto, en este caso fue usado en el componente **login** y el componente **navegacionUsuario** y posiblemente se pueda usar en el componente **perfil** también. Note que ambos componentes usaron el servicio de forma independiente sin tener que estar comunicados entre ellos, en este caso el uso del atributo **usuarioConectado** fue crucial para realizar esta independencia, si no existiera este atributo no habría manera de que el componente **navegacionUsuario** sepa que usuario ha ingresado, la única forma de poder saberlo sin el uso del servicio es que el componente **login** le pase el dato del usuario que ingreso al componente **vistaPrincipal** y luego este ultimo se lo pase al componente **navegacionUsuario** y se formaría una dependencia entre componentes muy alta e innecesaria y solo en este caso pequeño, imagine con otras acciones cuanta dependencia mas habría. Es por eso que el uso de servicios se hace especialmente importante en estos casos, siempre hay que encontrar la manera de que estos servicios puedan proporcionar independencia del uso de la información a través de el y evitar el acoplamiento y envió de información entre componentes que no estar correlacionados.

# Ajustes del proyecto para el uso de servicios

Como se menciono en la anterior sección, hay partes del proyecto que requieren una actualización, a continuación se mencionan algunas de ellas:
* Cuando dos usuarios entran al sistema a través del login, es necesario actualizar los datos del componente **navegacionUsuario** para que se vean los datos del ultimo usuario que ingreso.
* Cuando un usuario cierra sesión y se ve nuevamente el Login, los campos de texto aun tienen los datos que escribió el usuario cuando ingreso, se debería ver justo como cuando se inicia la aplicación
* Cuando se ingresa por segunda vez en una misma ejecución a la vista principal, no se muestra el componente **inicio** y se ve vacía la parte del panel principal.

En esta sección se van a realizar estas respectivas actualizaciones. Dentro del componente **login**, se crea un método llamado **restaurarValores**, dentro de este se debe dejar todo como cuando el usuario ejecuta la aplicación. 

Esto incluye: 
* Dejar el **JTextField tNombreUsuario** con el placeholder **Nombre Usuario** y el **JPasswordField tClaveUsuario** con el placeholder **Calve Usuario**.
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
  this.getLoginTemplate().getTClaveUsuario().setText("Clave Usuario");
  this.getLoginTemplate().getTClaveUsuario().setBorder(
      this.getLoginTemplate().getRecursosService().getBorderInferiorGris()
  );
  this.getLoginTemplate().getTClaveUsuario().setForeground(
      this.getLoginTemplate().getRecursosService().getColorGrisOscuro()
  );
  this.getLoginTemplate().getCbTipoUsuario().setSelectedIndex(0);
}
```

Ahora cuando se cierre sesión dentro de la **ventana principal** específicamente en la clase **VistaPrincipalComponent** en su método **mostrarComponentes** se va a llamar a este método:

<div align='center'>
    <img  src='https://i.imgur.com/0M6ATxH.png'>
    <p>Llamada del método restaurar valores del componente Login</p>
</div>

Dentro del componente **VistaPrincipal**, en este caso se quiere que cada vez que se inicie sesión siempre se vea el componente **Inicio** dentro de la ventana principal. De la misma manera, se crea un método llamado **restaurarValores** dentro de la clase **VistaPrincipalComponent**:
```javascript
public void restaurarValores(){
}
```

En este caso solo se llamará al panel **pPrincipal** y se le indica que incorpore el componente **Inicio** esto a través del objeto de su clase compañera **VistaPrincipalTemplate**:

```javascript
public void restaurarValores(){
  this.vistaPrincipalTemplate.getPPrincipal().add(inicioComponent.getInicioTemplate());
}
```

Ahora se debe llamar desde el **login** cada vez que se quiera ingresar:

<div align='center'>
    <img  src='https://i.imgur.com/emMxtUj.png'>
    <p>llamada del método restaurarValores de la vistaPrincipal</p>
</div>

Solo nos queda actualizar el componente **NavegaciónUsuario**, dentro de la clase **NavegaciónUsuarioComponent** se va a crear el método **ActualizarValores**:

```javascript
public void actualizarValores (){
}
```

* Lo primero que se debe hacer es actualizar el usuario que ha ingresado actualmente, para esto se llama al servicio **UsuarioService** nuevamente:

```javascript
public void actualizarValores (){
  this.usuarioConectado = sUsuario.getUsuarioConectado();
}
```

* Es necesario remover todo lo que este en el panel **pSuperior** ya que va a mostrar información nueva:

```javascript
public void actualizarValores (){
  this.usuarioConectado = sUsuario.getUsuarioConectado();
  this.navegacionUsuarioTemplate.getPSuperior().removeAll();
}
```

* Una vez se ha retirado todo del panel se llama al método **crearJLabels** el cual tomaba los datos del objeto **Usuario** que acaba de actualizarse:
```javascript
public void actualizarValores (){
  this.usuarioConectado = sUsuario.getUsuarioConectado();
  this.navegacionUsuarioTemplate.getPSuperior().removeAll();
  this.navegacionUsuarioTemplate.crearJLabels();
}
```

* Finalmente y para asegurarse de que el cambio se verá reflejado, se llama al método **repaint** para que realice la actualización de la ventana.
```javascript
public void actualizarValores (){
  this.usuarioConectado = sUsuario.getUsuarioConectado();
  this.navegacionUsuarioTemplate.getPSuperior().removeAll();
  this.navegacionUsuarioTemplate.crearJLabels();
  this.navegacionUsuarioTemplate.repaint();
}
```

Ya esta lista la actualización, solo falta que sea llamado este método, es posible aprovechar el método **restaurarValores** de la vista principal para que de una vez realice la actualización del componente **navegacionUsuario**.

<div align='center'>
    <img  src='https://i.imgur.com/PR22R2M.png'>
    <p>Llamada del método Actualizar Datos del componente NavegacionUsuario</p>
</div>

Ya se realizaron los ajustes necesarios y el programa funciona correctamente. Ahora es posible ingresar varias veces a la ventana principal desde una sola ejecución y con varios usuario y siempre mostrara la información del usuario que acaba de ingresar, también se puede notar que en la vista principal siempre estará incorporado el componente **inicio**. Por otro lado, una vez se cierra sesión el Login de usuario se verá con los valores iniciales.

# Resultado

Si has llegado hasta aquí **!Felicidades!** se ha aprendido como crear, cuando utilizar y como funcionan los **Servicios** dentro de la arquitectura. Se aprendió como gestionar un flujo de información externa de forma en que cada componente sea independiente en cuanto a obtener o enviar información a través del uso de servicios. Se aprendió ademas, las características principales de los **Servicios**. En la proxima clase se sigue usando servicios para revisar un objeto Gráfico particular, estos son las **JTables**.

# Actividad 

Implementar el uso de **Servicios Lógicos** en los proyectos para la gestión de la información externa.