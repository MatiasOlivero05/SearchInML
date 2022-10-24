# SearchInML
Búsqueda de productos en Mercado Libre


Descripción resumida de la APP.

Es una aplicación simple que lista productos del país ARGENTINA, consumidos desde una API del Marketplace Mercado Libre. 
Al hacer click en los productos se muestra más detalle del mismo y permite linkear al Marketplace en el producto seleccionado.


Descrición técnica del desarrollo.

- Aplicación realizada con patrones de diseño MVVM: código más limpio y extensible.
- Se utilizó la librería Retrofit2 para consumir la API: Por su eficacia, efiencias y se adapta a MVVM.
- Una activity con dos fragment. Un Fragment con un EditText y un botón para lanzar la búsqueda de productos a la API
  para crear un listado de productos (RecyclerView) y otro fragment para mostrar el detalle del mismo con la posibilidad 
  de linkearlo al Marketplace sobre el producto seleccionado.
- Se utilizó la librería Glide para mostrar en las imágenes desde las URLs en las ImageViews.
- Se utilizó la librería Gson para el paseo de los Json que devuelve la API y para traspasar datos entre Fragment.
- Se crearon algunos UnitTest y InstrumentedTest.
- Se manejan muchos de los posibles errores por objetos o valores nulos o vacíos.
- Se trató que sea un App con usabilidad simple, clara y acertada.



Datos de la API

GET https://api.mercadolibre.com/sites/MLA/search?q



Faltaría agregar

- Fondo de imagen por default o cuando falle.
- Mejoras de estilos
- Agregar más información proporcionada por la API.


