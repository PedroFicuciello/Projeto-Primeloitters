
create table Usuario(
IdUsuario int primary key auto_increment,
Nome varchar(50),
Login varchar(50),
Senha varchar(50)
);

create table Estabelecimentos(
IdEstab int primary key auto_increment,
codEstab varchar(10),
numMaquinas int,
estadoEstab varchar(8),
fk_Usuario int,
foreign key (fk_Usuario) references Usuario(idUsuario),
check (estadoEstab = 'verde' or estadoEstab = 'amarelo' or estadoEstab = 'vermelho')
);

create table Maquinas(
IdMaquina int primary key auto_increment,
tipoMaquina varchar(10),
numeroSerie varchar(12),
estadoFunc varchar(8),
memoria varchar(50),
valor_cpu int,
disco int,
fk_Estabelecimento int,
foreign key (fk_Estabelecimento) references Estabelecimentos(IdEstab),
check (tipoMaquina = 'computador' or tipoMaquina = 'servidor'),
check (estadoFunc = 'verde' or estadoFunc = 'amarelo' or estadoFunc = 'vermelho')
); 

create table Testes(
IdTeste int primary key auto_increment,
dataHora datetime,
tipoTeste varchar(30),
resultadoTeste varchar (30),
fk_Maquinas int,
foreign key (fk_Maquinas) references Maquinas(IdMaquina)
);

create table Dados(
dataHora datetime,
dadosCPU int,
dadosMemoria int,
dadosDisco int,
fk_Maquinas int,
foreign key (fk_Maquinas) references Maquinas(idMaquina)
);
