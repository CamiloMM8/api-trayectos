var bearer = "";
var apiKey = "";
var paradasActuales = [];
var mapaListo = false;
var API = "http://localhost:9005";

function initMap() {
  mapaListo = true;
  dibujarMapa(paradasActuales);
}

function dibujarMapa(json) {
  if (!mapaListo || !window.google || !google.maps) {
    return;
  }
  var conCoordenadas = json.filter(function (parada) {
    return parada.latitud != null && parada.longitud != null;
  });
  var centro = { lat: 4.43889, lng: -75.23222 };
  if (conCoordenadas.length > 0) {
    centro = { lat: conCoordenadas[0].latitud, lng: conCoordenadas[0].longitud };
  }
  var map = new google.maps.Map(document.getElementById("map"), {
    zoom: 13,
    center: centro,
  });
  var recorrido = [];
  for (var i = 0; i < conCoordenadas.length; i++) {
    var parada = conCoordenadas[i];
    var posicion = { lat: parada.latitud, lng: parada.longitud };
    recorrido.push(posicion);
    new google.maps.Marker({
      position: posicion,
      label: String(parada.ordenParada),
      title: parada.ordenParada + ". " + parada.ubicacion + " (" + parada.nombres + " " + parada.apellidos + ")",
      map: map,
    });
  }
  if (recorrido.length > 1) {
    new google.maps.Polyline({
      path: recorrido,
      geodesic: true,
      strokeColor: "#1a73e8",
      strokeOpacity: 1,
      strokeWeight: 3,
      map: map,
    });
  }
}

function setEstado(texto) {
  document.getElementById("estado").textContent = texto;
}

function cargarRuta() {
  var codigo = document.getElementById("codigo").value.trim();
  if (!bearer) {
    setEstado("Inicia sesion para consultar la ruta.");
    return;
  }
  if (!codigo) {
    setEstado("Escribe el codigo de ruta.");
    return;
  }
  fetch(API + "/ProyectoV1/trayecto/ruta/" + encodeURIComponent(codigo), {
    method: "GET",
    headers: {
      Authorization: bearer,
      apikey: apiKey,
    },
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("No se pudo consultar la ruta (" + response.status + ")");
      }
      return response.json();
    })
    .then(function (json) {
      paradasActuales = json;
      var conCoordenadas = json.filter(function (parada) {
        return parada.latitud != null && parada.longitud != null;
      }).length;
      setEstado(json.length + " paradas, " + conCoordenadas + " en el mapa");
      dibujarMapa(json);
    })
    .catch(function (error) {
      setEstado(error.message);
    });
}

document.getElementById("btnLogin").addEventListener("click", function () {
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;
  apiKey = document.getElementById("apikey").value;
  setEstado("Autenticando...");
  fetch(API + "/authenticate", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      apikey: apiKey,
    },
    body: JSON.stringify({
      username: username,
      password: password,
    }),
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Credenciales o API Key invalidas");
      }
      return response.json();
    })
    .then(function (data) {
      bearer = data.token;
      cargarRuta();
    })
    .catch(function (error) {
      setEstado(error.message);
    });
});

document.getElementById("btnRefrescar").addEventListener("click", cargarRuta);
window.initMap = initMap;
