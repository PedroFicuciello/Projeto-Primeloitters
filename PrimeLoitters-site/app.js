process.env.NODE_ENV = 'production';

var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usuariosRouter = require('./routes/usuarios');
var leiturasRouter = require('./routes/leituras');
var estabelecimentosRouter = require('./routes/estabelecimentos');
var maquinasRouter = require('./routes/maquinas');
var alertasRouter = require('./routes/alertas');
var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/usuarios', usuariosRouter);
app.use('/leituras', leiturasRouter);
app.use('/estabelecimentos', estabelecimentosRouter);
app.use('/maquinas', maquinasRouter);
app.use('/alertas', alertasRouter);

module.exports = app;
