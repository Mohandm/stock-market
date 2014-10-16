//Define a function scope, variables used inside it will NOT be globally visible.
(function () {

    var
    message, action,
    //Define the main module.
        vsmApp = angular.module('vsmApp', 
            ['ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch','ui.bootstrap','ui.grid','easypiechart']);

    vsmApp.config(function ($routeProvider, $httpProvider) {
        //configure the rounting of ng-view
       $routeProvider
        .when('/', {
            templateUrl: 'app/views/dashboard.html',
            controller: 'DashboardController'
        })
       .when('/myPortfolio', {
           templateUrl: 'app/views/myPortfolio.html',
           controller: 'MyPortfolioController'
       })
       .when('/form/:formName', {
            templateUrl: 'app/views/form.html',
            controller: 'FormSelectorController'
        })
        .when('/action/:actionCode/:value', {
            templateUrl: 'app/views/result.html',
            controller: 'ActionController'
        })
        .otherwise({
            redirectTo: '/'
        });

      //configure $http to catch message responses and show them
        $httpProvider.responseInterceptors.push(function ($q, $timeout) {
            
            var setMessage = function (response) {
                //if the response hvsmApp a text and a type property, it is a message to be shown
                if (response.data && response.data.text && response.data.type) {
                    message = {
                        text: response.data.text,
                        type: response.data.type,
                        show: true
                    };
                    $timeout(function(){message = {};}, 5000);  
                }
            };
            return function (promise) {
                return promise.then(
                    //this is called after each successful server request
                    function (response) {
                        setMessage(response);
                        return response;
                    },
                    //this is called after each unsuccessful server request
                    function (response) {
                        setMessage(response);
                        return $q.reject(response);
                    }
                );
            };
        });
    });
    
    vsmApp.run(function ($rootScope,$http) {
        //make current message accessible to root scope and therefore all scopes
        $rootScope.message = function () {
            return message;
        };
        
    });
}());