(function () {
var vsmApp = angular.module('vsmApp');
 
vsmApp.service('DialogService', function($compile, $http, $rootScope, $templateCache, $cacheFactory) {
    this.open = function(options) {
    	
    	$http.get('app/directive_templates/dialog/standardModal.html').success(function (template) {
	        var childScope = $rootScope.$new();
	        childScope.title = options.title;
	        childScope.content = options.content;
	        childScope.customButtons = options.customButtons;
	        childScope.showButtonClose = options.showButtonClose;   
	        childScope.showStaticContent = options.showStaticContent;   
	        childScope.showSearch = options.showSearch;   
	        childScope.closeText = options.closeText;
	        childScope.form = options.form;
	        childScope.mappings = options.mappings;
	        childScope.targetURL = options.targetURL;
	 
	        $('body').append($compile(template)(childScope));
	        $('#dialogModal').modal();
	 
	        $('#dialogModal').on('hidden.bs.modal', function (e) {
	            childScope.$destroy();
	            $('#dialogModal').remove();
	        }); 
    	});
    };    
    this.close = function() {        
        $('#dialogModal').modal('hide');
    };
});
 

vsmApp.service('modals', function(DialogService,$http) {
    this.showInfo = function(title, content) {
        DialogService.open({            
            title: title,
            content: content, 
            showButtonClose: true,
            showStaticContent: true,
            closeText: "Ok"
        });
    };
    
    this.showConfirmation = function(title, content, acceptCallback) {
        
    	DialogService.open({            
            title: title,
            content: content, 
            showStaticContent: true,
            customButtons: [
            	{
            		index:1,
            		name:"Confirm",
            		callback:acceptCallback,
            		cssClass:"btn btn-success"
            	}
            ],
            showButtonClose: true,
            closeText: "Cancel"
        });
    };
    
    this.showSearch = function(title, form, mappings, targetURL) {
    		DialogService.open({            
                title: title,
                showButtonClose: true,
                closeText: "Ok",
                targetURL:targetURL,
                form:form,
                showSearch:true,
                mappings:JSON.parse(mappings)
       });
    };
    
    this.showEntities = function(form,mappings) {
    	this.showSearch("List Of Entities",form,mappings,"jsonFiles/listOfEntity.json");
    };
    
    this.showCurrencies = function(form,mappings) {
    	this.showSearch("List Of Currencies",form,mappings,"jsonFiles/listOfCurrencies.json");
    };
    
    this.close = function()
    {
    	DialogService.close();
    };
 });
}());
 