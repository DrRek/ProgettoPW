var toDoD = 'asyncGenericSearch';
var sortD = 'nome';
$(document).ready(function() {

	initialize();
	setSearchField();

	$("#advancedSearch").click(function(event) {
		//qui dovrei validare gli input
		toDoD = 'asyncSpecificSearch'
			advancedSearch();
	});
});

function formatData(responseText) {
	var toAppend = '<h2>Products</h2><table class="table-bordered"><thead><tr><th><a href="#" onclick="return orderByName()">Nome</a></th><th><a href="#" onclick="return orderByMarca()">Marca</a></th><th><a href="#" onclick="return orderByTipo()">Tipo</a></th><th><a href="#" onclick="return orderByPrezzo()">Prezzo</a></th></tr></thead>';
	$.each(responseText, function(i, articleObject) {
		console.log(articleObject);
		if(articleObject.disponibilita > 0){
			toAppend += '<tr><td><a href="articlePage?nome=' + articleObject.nome
			+ '&marca=' + articleObject.marca + '">' + articleObject.nome
			+ '</a></td>';
			toAppend += '<td>' + articleObject.marca + '</td>';
			toAppend += '<td>' + articleObject.tipo + '</td>';
			toAppend += '<td>' + articleObject.prezzo + '</td></tr>';
		}
	});
	toAppend += '</table>';
	$("#demos").html(toAppend);
};

function setSearchField() {
	if ($('select[name=tipo]').val() == "O") {
		$(".specificiPerOcchiali").show();
		$(".specificiPerLentine").hide();
	} else {
		$(".specificiPerOcchiali").hide();
		$(".specificiPerLentine").show();
	}
}

function orderByName() {
	sortD = 'nome';
	if (toDoD == "asyncGenericSearch") {
		asyncGenericSearch();
	}
	else {
		advancedSearch();
	}
}

function orderByMarca() {
	sortD = 'marca';
	if (toDoD == "asyncGenericSearch") {
		asyncGenericSearch();
	}
	else {

		advancedSearch();
	}
}

function orderByTipo() {
	sortD = 'tipo';
	if (toDoD == "asyncGenericSearch") {
		asyncGenericSearch();
	}
	else {
		advancedSearch();
	}
}

function orderByPrezzo() {
	sortD = 'prezzo';
	if (toDoD == "asyncGenericSearch") {
		asyncGenericSearch();
	}
	else {
		advancedSearch();
	}
}

function advancedSearch() {
	var tipo = $('select[name=tipo]').val();
	var daCercare = $('input[name=daCercare1]').val();
	if (daCercare == null || daCercare.length == 0) {
		daCercare = $('input[name=daCercare]').val();
	}
	var marca = $('select[name=marca]').val();
	var prezzoMin = $('input[name=prezzoMin]').val();
	var prezzoMax = $('input[name=prezzoMax]').val();
	if (tipo == "O") {
		var sesso = $('select[name=sesso]').val();
		var colore = $('input[name=colore]').val();
		$.ajax({
			type : "GET",
			url : "article",
			data : {
				toDo : 'asyncSpecificSearch',
				daCercare : daCercare,
				tipo : tipo,
				marca : marca,
				prezzoMin : prezzoMin,
				prezzoMax : prezzoMax,
				sesso : sesso,
				colore : colore,
				sort : sortD
			},
			dataType : "json",
			error : function(xhr, status, errorThrown) {
				console.log(JSON.stringify(xhr));
				console.log("AJAX error: " + status + ' : ' + errorThrown);
			},
			success : function(responseText) {
				formatData(responseText);
			}
		})
	} else {
		var gradazione = $('select[name=gradazione]').val();
		var tipologia = $('select[name=tipologia]').val();
		var raggio = $('input[name=raggio]').val();
		var diametro = $('input[name=diametro]').val();
		var colore = $('select[name=colore]').val();
		$.ajax({
			type : "GET",
			url : "article",
			data : {
				toDo : 'asyncSpecificSearch',
				daCercare : daCercare,
				tipo : tipo,
				marca : marca,
				prezzoMin : prezzoMin,
				prezzoMax : prezzoMax,
				gradazione : gradazione,
				tipologia : tipologia,
				raggio : raggio,
				diametro : diametro,
				colore : colore,
				sort : sortD
			},
			dataType : "json",
			success : function(responseText) {
				formatData(responseText);
			}
		})
	}
}

function asyncGenericSearch() {
	toDoD = 'asyncGenericSearch'
		$.ajax({
			type : "GET",
			url : "article",
			data : {
				toDo : 'asyncGenericSearch',
				daCercare : $('input[name=daCercare1]').val(),
				sort : sortD
			},
			dataType : "json",
			error : function(xhr, status, errorThrown) {
				console.log(JSON.stringify(xhr));
				console.log("AJAX error: " + status + ' : ' + errorThrown);
			},
			success : function(responseText) {
				console.log(responseText);
				formatData(responseText);
			}
		})
}

function initialize(){
	if($('input[name=daCercare1]').val() == "cercaPerTipo"){
		$('input[name=daCercare1]').val("");
		toDoD = 'asyncSpecificSearch';
		advancedSearch();
	}
	else{
		asyncGenericSearch();
	}
}