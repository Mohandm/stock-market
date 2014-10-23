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
            childScope.formURL = options.formURL;
            childScope.showForm = options.showForm;
            childScope.modalSize = options.modalSize;
            childScope.passValuesToDialog = options.passValuesToDialog;

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
 

vsmApp.service('modals', function(DialogService,$http,$timeout) {
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

    this.showForm = function(title, form, passValuesToDialog , modalSize) {
            
        var openForm = function() {
            DialogService.open({            
                title: title,
                showForm: true,
                formURL:'app/views/'+form+'.html',
                passValuesToDialog:passValuesToDialog,
                modalSize:modalSize
            });
        }
        DialogService.close();
        $timeout(openForm,500);
    };
     
    this.close = function()
    {
    	DialogService.close();
    };
 });
}());
 