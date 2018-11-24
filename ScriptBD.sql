use api

create table Rol(
id int not null,
descripcion varchar(45),
PRIMARY KEY (id)
)


create table Usuario(
dni varchar(8) not null,
nombreUsuario varchar(20),
contrasenia varchar(20),
nombre varchar(45),
apellido varchar(45),
domicilio varchar(45),
fechaNac date,
mail varchar(200),
estado bit,
PRIMARY KEY (dni),
)

create table UsuarioRol(
idRol int not null,
idUser varchar(8) not null,
estado bit not null,
primary key(idRol,idUser),
CONSTRAINT FK_rol_id_rol FOREIGN KEY (idRol) references Rol (id),
CONSTRAINT fk_user_id_user FOREIGN KEY (idUser) references Usuario (dni)
)

select * from Rol
select *from Usuario
select * from UsuarioRol where idUser='50'
select * from sala

insert into UsuarioRol values (3, '1', 'true')
insert into funcion values ('avengers', 'ingles', '1', 'sala 1', 'atlas aa', '10:30:00', '2018-10-30', 'true')

insert into Rol values(1,'Cliente')
insert into Rol values(4,'Vendedor')
insert into Rol values(2,'Administrador')
insert into Rol values(3,'Operador')
insert into Rol values(5,'Agente Comercial')

create table Cine(
idCine int identity(1,1),
cuit varchar(11) not null,
nombre varchar(45) PRIMARY KEY,
domicilio varchar(45),
cantidadSalas int,
capacidadTotal int,
idAdministrador varchar(8) not null,
estado bit,
CONSTRAINT FK_admin FOREIGN KEY (idAdministrador) references Usuario (dni)
)

create table Sala(
nombreCine varchar(45) not null,
nombre varchar(45) not null,
estado bit not null,
idOperador varchar(8) not null,-- EN REALIDAD DEBERIA SER ADMINISTRADOR BURROS
CONSTRAINT FK_cine FOREIGN KEY (nombreCine) references Cine (nombre),
CONSTRAINT FK_salauser FOREIGN KEY (idOperador) references Usuario (dni),
PRIMARY KEY(nombre,nombreCine)
)

create table Pelicula(
nombre varchar(45) not null,
director varchar(20),
genero varchar(20),
duracion int,
idioma varchar(15) not null,
subtitulos bit,
calificacion varchar(10),
observacion varchar(200),
idOperador varchar(8) not null,
estado bit,
CONSTRAINT FK_pelicula FOREIGN KEY (idOperador) references Usuario (dni),
PRIMARY KEY(nombre, idioma)
)
select * from funcion
create table Funcion(
idFuncion int identity(1,1),
--Claves candidatas de Pelicula
nombrePelicula varchar(45) not null, 
idiomaPelicula varchar(15) not null,
idOperador varchar(8) not null,
--PK SALA
nombreSala varchar(45) not null,
nombreCineSala varchar(45) not null,
horario time not null,
dia date not null,
estado bit,
PRIMARY KEY (idFuncion, horario, dia),
CONSTRAINT FK_funcion1 FOREIGN KEY (nombrePelicula, idiomaPelicula)references Pelicula (nombre,idioma),
CONSTRAINT FK_operadorF FOREIGN KEY (nombreSala,nombreCineSala) references Sala (nombre,nombreCine),
CONSTRAINT FK_operador FOREIGN KEY (idOperador) references Usuario (dni)
)

create table AsientoFisico(
nombreSala varchar(45) not null,
nombreCineSala varchar(45) not null,

fila int not null,
columna int not null,

estado bit,
PRIMARY KEY(fila, columna, nombreSala, nombreCineSala),
CONSTRAINT FK_sala FOREIGN KEY (nombreSala,nombreCineSala) references Sala (nombre,nombreCine) 
)

create table AsientoVendido(
--Claves candidatas de Asiento fisico
filaFisico int not null,
columnaFisico int not null,
nombreSala varchar(45) not null,
nombreCineSala varchar(45) not null,

--Clave candidate de Funcion
idFuncion int not null,
horarioFuncion time not null,
diaFuncion date not null,

idAsientoV int identity(1,1),
estado bit,

PRIMARY KEY(idAsientoV, idFuncion),
CONSTRAINT FK_asiento FOREIGN KEY (filaFisico,columnaFisico,nombreSala,nombreCineSala) references AsientoFisico (fila,columna,nombreSala,nombreCineSala),
CONSTRAINT FK_FuncVend FOREIGN KEY (idFuncion,horarioFuncion,diaFuncion) references Funcion (idFuncion,horario,dia)

)

create table Entrada(
idEntrada int identity(1,1) primary key,
--Claves candidatas de Funcion
idFuncion int not null,
horarioFuncion time not null,
diaFuncion date not null,

estadoRetiro bit, --Es el estado de una entrada cuando es comprada online, P= pendiente, R= retirada. Si es por boleteria se pondra directo estado R
--Clave Asiento
idAsientoV int not null,
precio float,

CONSTRAINT FK_asiento1 FOREIGN KEY (idAsientoV,idFuncion) references AsientoVendido (idAsientoV,idFuncion),
CONSTRAINT FK_funcion2 FOREIGN KEY (idFuncion,horarioFuncion,diaFuncion) references Funcion (idFuncion,horario,dia)
)
delete from sala
select * from sala
select * from cine
---------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Promocion(
id int identity(1,1) PRIMARY KEY,
idAgente varchar(8) not null,
estado bit,
detalle varchar(45), --descripcion si es 2x1, descuento, etc
porcentaje float, --es el atributo del descuento e incluso que podria usarse en 2x1 ya que en si es un descuento
CONSTRAINT FK_agente FOREIGN KEY (idAgente) references Usuario (dni)
)

CREATE TABLE PromoCombo(
idCombo int,
idPromo int not null,
CONSTRAINT FK_combo FOREIGN KEY (idPromo) references Promocion (id)
)

create table Venta(
codigoVenta int identity(1,1) primary key,
monto float,
fecha date,
)

CREATE TABLE PromoVenta(
codigoVenta int not null,
idPromo int,
primary key(codigoVenta, idPromo),
CONSTRAINT FK_codVta FOREIGN KEY (codigoVenta) references Venta (codigoVenta),
CONSTRAINT FK_promoID FOREIGN KEY (idPromo) references Promocion (id)
)



create table EntradaVenta(
idVenta int not null,
idEntrada int not null,
primary key(idVenta,idEntrada),
CONSTRAINT FK_ventaEntrada FOREIGN KEY (idVenta) references Venta (codigoVenta),
CONSTRAINT FK_entrada FOREIGN KEY (idEntrada) references Entrada (idEntrada)
)

create table Tarjeta(
tipo varchar(45),
vencimiento varchar(12),
numero varchar(20) not null,
codigo varchar(5) not null,
titular varchar(45) not null,

--dniTitular varchar(8) not null,--es el enlace de tarjeta usuario. Con esto indico que es la tarjeta de fulano
--CONSTRAINT FK_TITULAR foreign key(dniTitular) references Usuario (dni),

PRIMARY KEY(numero)
)
drop table Tarjeta
drop table online

create table Online(
codigoVenta int not null,
idCliente varchar(8) not null,
idTarjeta varchar(20) not null,

CONSTRAINT FK_venta FOREIGN KEY (codigoVenta) references Venta(codigoVenta),
CONSTRAINT FK_cliente FOREIGN KEY (idCliente) references Usuario(dni),
CONSTRAINT FK_tarjeta FOREIGN KEY (idTarjeta) references Tarjeta(numero)
)


create table Boleteria(
codigoVenta int not null,
idUsuario varchar(8) not null,

primary key(codigoVenta,idUsuario),
CONSTRAINT FK_venta2 FOREIGN KEY (codigoVenta) references Venta (codigoVenta),
CONSTRAINT FK_vendedor FOREIGN KEY (idUsuario) references Usuario (dni),
)

create table BoleteriaTarjetaCredito(
codigoVenta int not null,
nroTarjeta varchar(20) not null

primary key(codigoVenta,nroTarjeta),
CONSTRAINT FK_venta3 FOREIGN KEY (codigoVenta) references Venta (codigoVenta),
CONSTRAINT FK_tarjeta1 FOREIGN KEY (nroTarjeta) references Tarjeta(numero)
)