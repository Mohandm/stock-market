var vsmApp = angular.module("vsmApp");

vsmApp.directive('validate',['$http','ValidatorService', function($http,ValidatorService) {
   return {
     restrict: 'A',
     require: 'ngModel',
     link: function(scope, element, attrs, ctrl) {
    	 
    	
    	 var validator = function(formValue){
        	if (attrs.validations)
        	 {
        		 var validations = JSON.parse(attrs.validations);
	        	 var valid;
	        	 angular.forEach(validations, function(value, key){
	        		 if (value.validator)
	     			{
	        			 if (value.scopeValue)
	        			 {
	        				 valid = ValidatorService[value.validator](formValue,scope.formmodel[value.scopeValue]);
		        			 ctrl.$setValidity(value.event, valid);
	        			 }
	        			 else if (value.serverSide)
	        			 {
	        				 if (ctrl.$valid && !ctrl.$error[value.event])
	        		        	{	 
	        			    		 $http.post(value.validator,formValue).success(function (valid) {
			        					 if (valid == "true") {
			        						 ctrl.$setValidity(value.event, true);
			        					 }
			        					 else if (valid == "false"){
			        						 ctrl.$setValidity(value.event, false);
			        					 }
	        			    		});
	        		        	}
	        				 ctrl.$setValidity(value.event, true);
	        			 }
	        			 else
	        			 {
	        				 valid = ValidatorService[value.validator](formValue);
		        			 ctrl.$setValidity(value.event, valid);
	        			 }
	     			}
	       	     });
        	 }
             // return the value or nothing will be written to the DOM.
        	 return formValue;
         };
         		
	     //Validate when the view value changes in the DOM 
         ctrl.$parsers.unshift(validator);
	     
         //Validate when the model value changes
         ctrl.$formatters.unshift(validator);
	      
         //Set up watches on the dependent scope values to validate when they change
         if (attrs.validations)
     	 {
     		 var validations = JSON.parse(attrs.validations);
        	 angular.forEach(validations, function(value, key)
        	{
        		if (value.scopeValue) 
        		{
        			scope.$watch('formmodel.'+value.scopeValue, function(newVal,oldVal){
                       if(newVal !== oldVal){
                    	  validator(element.val());  
                       }
                   }, true);
        		}
        		
        	 });
     	 }
   }
};
}]);