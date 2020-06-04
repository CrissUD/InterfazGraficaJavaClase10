# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 10

## Objetivos

* Examinar las características principales de los servicios y su forma de construcción.
* Reconocer el propósito del uso de Servicios dentro del proyecto para la obtención de información externa desde cualquier componente gráfico.
* Identificar las distintas funcionalidades que puede tomar un servicio para el manejo de información externa.
* Comprender la forma en que distintas partes de nuestro proyecto puede dar uso de servicios para obtener información.

# Antes de Comenzar

Para continuar con el ejercicio deberá actualizar la carpeta **resources/img/perfiles** ya que se han agregado nuevas imágenes. Estas las puede descargar en este mismo repositorio entrando a la carpeta **Clase10** seguido de **resources/img/perfiles**.

Vamos a observar mediante un modelo estructural general como es la estructura de nuestro proyecto hasta el momento:

<div align='center'>
    <img  src='https://i.imgur.com/8q6Uq6C.png'>
    <p>Modelo Estructural de nuestro Proyecto</p>
</div>

El anterior es un esquema general de como esta nuestro proyecto, hay muchas cosas que se han omitido y solo se esta mostrando lo más relevante de nuestro proyecto en cuanto a atributos, métodos y relaciones entre clases.

* Vamos a crear un nuevo paquete desde nuestra carpeta raíz **src** el cual llamaremos **archives** y ahi contendremos dos archivos que servirán como una simulación de información externa. 

Recordando un poco nuestro recorrido, hemos utilizado los eventos de mouse para darle interactividad a varias partes de nuestro proyecto, acciones como el arrasate de la ventana, el cambio de color de varios botones y labels, la gestión del contenido y color de los JTetField etc. Ademas mientras realizábamos dicha interactividad vimos algunos temas importantes relacionados con los eventos, cosas como: **Representación única para objetos gráficos de una misma Clase** , **Discriminación de Clases**, **Efectos hacia otros objetos Gráficos** y el **Uso combinado de varios Métodos implementados de eventos**.

# Servicios 

En esta sesión vamos a ver el uso y características de los servicios y para ello vamos a ver algunos items como:
* **Explicación general de los servicios**.
* **Servicio contenedor de información externa**.
* **Servicio que recibe información externa**.
* **Ajustes de nuestro proyecto para el uso de servicios**.


# Explicación general de los servicios

Antes hemos hablado de los **Componentes Gráficos** y sabemos hasta este punto que estos están conformados por una parte visual (**Template**), y una parte lógica (**Component**). Aunque la clase **Component** utiliza lógica detrás para que todas las funcionalidades del componente se puedan cumplir, hay acciones que no deben realizar, recordemos que esta clase se debe encargar unicamente de soportar la lógica que requiere el componente.

Nuestras aplicaciónes de interfaz Gráfica siempre van a depender de la obtención de información externa que el usuario va a solicitar, ya sea de un servidor web, una base de datos externa, una Api etc. Podríamos delegar la obtención de esta información a nuestras clases **Components** y es una forma de hacerlo y funciona. Sin embargo esto va a restar reutilización y modularidad a nuestro proyecto. Esta información externa tiene ciertos datos que seguramente van a ser solicitados por varias partes de nuestro proyecto y si en cada componente que se necesite dicha información se va a consumir un mismo servicio web externo esto podría restar rendimiento, otra forma de hacerse es obtener la información externa una vez y empezar a pasar la información entre componentes, pero esta practica hará que nuestro proyecto sea muy acoplado entre componentes y exista una dependencia alta entre componentes gráficos.

Podemos hacer uso de servicios que van a tener un propósito estrecho y bien definido y que puede encapsular alguna funcionalidad o información externa que será requerida por varias partes de nuestro proyecto. Estas clases entonces se encargan principalmente de contener la información que se va a obtener externamente. Pueden existir varios tipos de servicios:
* **Servicios Lógicos:** Que se encargan de contener información externa y que sera solicitada por varas partes de nuestro proyecto. Siempre serán solicitados por las clases **Component** de nuestros componentes.
* **Servicios Gráficos:** Que contiene una funcionalidad o información especifica y relacionada con la creación de objetos gráficos. Siempre serán solicitados por las clases **Template**.

Independientemente de su propósito la estructura de un servicio es similar, podemos crear servicios **Singleton** o podemos realizar varios objetos de un servicio (Esto en ocasiones muy especiales).

Antes hemos visto y usado a lo largo del curso el uso de **Servicios Gráficos**, hemos creado:
* El servicio Gráfico **ObjGraficosService** encargado de encapsular la construcción de objetos gráficos.
* El servicio Gráfico **RecursosService** encargado de la creación responsable de objetos decoradores que puedan ser solicitados por varios template.

En los anteriores servicios podemos ver los dos propósitos principales de los servicios:

* **Contener Funcionalidad**: En el servicio **ObjGraficosService** tenemos encapsulada una funcionalidad que es usada por la mayoría de clases Template que hemos creado. Este servicio contiene una serie de métodos encargados de la construcción de objetos gráficos y que ayuda a las clases **Template** con el proceso de **creación de objetos gráficos**. Esta funcionalidad es común y general por lo que permite que se use en varias partes del proyecto e incluso en varios proyectos.
* **Contener Información**: En el servicio **RecursosService** de alguna manera se esta conteniendo información, no es información externa evidentemente ni tampoco información compuesta de datos comúnes. Pero si se ve a nivel general este servicio crea objetos decoradores (información) que sera solicitada desde varias partes de nuestro proyecto.

Ahí esta la clave del uso de los servicios, estos son solicitados por varias partes del proyecto y evita el acoplamiento extremo entre componentes gráficos. Por ejemplo si el servicio **RecursosService** no existiera pero se quisiera controlar la creación de objetos decoradores como colores, fuentes etc. La única forma de realizar esto es creándolos todos desde la clase **App** y empezar a repartir estos objetos mediante los constructores de los componentes y esto generaría un altísimo acoplamiento entre clases.


# Servicio contenedor de información externa

Hay veces que la información externa es necesaria solo para propósitos visuales, es decir información que sera mostrada pero que no necesariamente esta contenida en algún servidor web o una base de datos, simplemente es información que se quiere mostrar en la interfaz gráfica pero que no es tan importante para guardarse en una base de datos o un servidor web externo.

