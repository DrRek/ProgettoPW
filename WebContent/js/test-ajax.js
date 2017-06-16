$(document).ready(function() {
        $('#test').click(function(event) {
                var name = 'ajax';
                $.get('article', {
                        action : name
                }, function(responseText) {
                	$.each(responseText, function(i, articleObject) 
				    		{
				    	 		$("#demo").append(articleObject.nome + "<br>");
				    		});
                });
        });
});