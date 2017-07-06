$(document).ready(function(){
	$("#aSubmit").click(function(event){
		addArticleToPromotion();
	});
});

function addArticleToPromotion(){
	var promozione = $("#aPromotion").val();
	var nome = $("#aName").val();
	var marca = $("#aBrand").val();
	$.ajax({
		type : "POST",
		url : "promotion",
		async : true,
		data : {
			action : 'addArticleToPromotion',
			promozione : promozione,
			nome : nome,
			marca : marca
		},
		dataType : "json",
		error : function(xhr, status, errorThrown) {
			alert("No article matching this value!")
			console.log(JSON.stringify(xhr));
			console.log("AJAX error: " + status + ' : ' + errorThrown);
		},
		success : function(responseText) {
			formatDataArticleInPromotion(responseText);
		}
	});
}

function formatDataArticleInPromotion(responseText){
	var toAppend = '<thead><tr><th>Name</th><th>Brand</th></tr></thead>';
	$.each(responseText, function(i, validi) {
			toAppend += '<tr><td>' + validi.nome + '</td>';
			toAppend += '<td>' + validi.marca + '</td></tr>';
	});
	$("#tableIncludedArticle").html(toAppend);
}