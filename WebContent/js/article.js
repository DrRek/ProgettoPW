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

function formatData(responseText)
{
	var toAppend = '';
	$.each(responseText, function(i, articleObject) {
		console.log(articleObject);
		if(articleObject.disponibilita > 0){
			toAppend += '<div class="block">'
							+'<div class="top">'
								+ '<ul>'
								+ '<li><a href="#"><i class="fa fa-star-o" aria-hidden="true"></i></a></li>'
								+ '<li><span class="prodotto">' + articleObject.marca + '</span></li>'
								+ '<li><a href="#"><i class="fa fa-shopping-basket" aria-hidden="true"></i> </a></li>'
								+ '</ul>'
								+'</div>';
				toAppend += '<div class="middle">';
			
				if(articleObject.tipo == 'O')
					toAppend += '<img src="image/placeholder_occhiali.jpg" alt="pic" />';
				else
					toAppend += '<img src="image/placeholder_lentine.jpg" alt="pic" />';
				toAppend += '</div>'
						 +  '<div class="bottom">'
	    	   			 + '<div class="heading">'+ articleObject.nome +'</div>'
	    	   			 + '<div class="info">' + articleObject.disponibilita + ' pezzi disponibili</div>' 
	    	   			 + '<div class="price">' + articleObject.prezzo + ' €</div>' 
	    	   			 + '<div><a href="articlePage?nome=' + articleObject.nome
	    	   				+ '&marca=' + articleObject.marca + '">Mostra</a></div>'
	    	   			 + '</div>'
						+'</div>';
		}
	});
	$("#demos").html(toAppend);
}

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