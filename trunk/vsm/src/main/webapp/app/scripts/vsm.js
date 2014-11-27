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
    'ngTouch','ui.bootstrap','ui.grid','easypiechart','nvd3ChartDirectives','angularFileUpload']);

    vsmApp.config(function ($routeProvider, $httpProvider) {
        //configure the rounting of ng-view
       $routeProvider
        .when('/', {
           templateUrl: 'app/views/dashboard.html',
           controller: 'DashboardController',
           needsTransition : true 
        })
       .when('/myPortfolio', {
           templateUrl: 'app/views/myPortfolio.html',
           controller: 'MyPortfolioController',
           needsLogin : true,
           needsTransition : true
       })
       .when('/leaderBoard', {
           templateUrl: 'app/views/leaderBoard.html',
           controller: 'LeaderBoardController',
           needsTransition : true
       })
       /*.when('/leagues', {
           templateUrl: 'app/views/leagues.html',
           controller: 'LeaguesController',
           needsLogin : true,
           needsTransition : true
       })*/
       .when('/followers', {
           templateUrl: 'app/views/followers.html',
           controller: 'FollowersController',
           needsLogin : true,
           needsTransition : true
       })
        .when('/charts', {
           templateUrl: 'app/views/charts.html',
           controller: 'ChartsController',
           needsTransition : true
       })
        .when('/alerts', {
            templateUrl: 'app/views/alerts.html',
            controller: 'AlertsController',
           needsLogin : true,
           needsTransition : true
        })
       .when('/watchList', {
           templateUrl: 'app/views/watchList.html',
           controller: 'WatchListController',
           needsLogin : true,
           needsTransition : true
       })
       .when('/myDashboard', {
           templateUrl: 'app/views/myDashboard.html',
           controller: 'MyDashboardController',
           needsLogin : true,
           needsTransition : true
       })
       .when('/myAchievements', {
           templateUrl: 'app/views/myAchievements.html',
           controller: 'AchievementController',
           needsLogin : true,
           needsTransition : true
       })
       .when('/userHome', {
           templateUrl: 'app/views/userHome.html',
           controller: 'HomeController',
           needsLogin : true,
           needsTransition : true
       })
       .when('/form/:formName', {
            templateUrl: 'app/views/form.html',
            controller: 'FormSelectorController'
        })
        .when('/action/:actionCode/:value?', {
            templateUrl: 'app/views/result.html',
            controller: 'ActionController'
        })
        .when('/tutorials', {
           templateUrl: 'app/views/tutorial.html'
        })
        .otherwise({
            redirectTo: '/'
        });

        //Allow Cross Domain Requests
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        // Configure $http to catch authentication error responses
        $httpProvider.interceptors.push(['$injector',function ($injector) {
            return $injector.get('AuthInterceptorService');
        }]);

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
                }
                // Clear messages after some time
                $timeout(function(){message = {show: false};}, 8000);
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
    
    vsmApp.run(function ($rootScope,$http, AlertsService, $location,TourService, AuthService, AUTH_EVENTS,$timeout, AchievementService) {

        FastClick.attach(document.body);

        //make current message accessible to root scope and therefore all scopes
        $rootScope.message = function () {
            return message;
        };

        $rootScope.isAuthenticated = function () {
            return AuthService.isAuthenticated();
        };

        $rootScope.getFinalURL = function(url)
        {
            //Change this for Mobile Apps
            /*var finalUrl = 'http://localhost:9091/stockmarket/';*/
            var finalUrl = '';
            return finalUrl+url;
        };

        $rootScope.onPageLoad = function(floatingShareOptions){
            var alertsListPromise = AlertsService.getUserNotifications();
            alertsListPromise.then(function(data){
                $rootScope.alertsList = data.alertsList;
                $rootScope.notificationsList = data.notificationsList;
            });

            var title = ' Misys - Stock Market League'
                ,desc = '(A Game to Enthrall and Engage you on Stock Markets!).'
                , url = 'http://www.misys.com/';
            if(floatingShareOptions)
            {
                if(floatingShareOptions.title)
                {
                    title = floatingShareOptions.title;
                }
                if(floatingShareOptions.description)
                {
                    desc = floatingShareOptions.description;
                }
                if(floatingShareOptions.url)
                {
                    url = floatingShareOptions.url;
                }
            }
            $("body").floatingShare({
                place: "top-right",
                buttons: ["facebook","twitter","linkedin"],
                title : title,
                description : desc,
                url : url
            });

            if($('#flexHome').length){

                $('#flexHome').flexslider({
                    animation: "slide",
                    controlNav:true,
                    directionNav:false,
                    touch: true,
                    direction: "vertical",
                    slideshowSpeed : 3000
                });
            }
        };

        $rootScope.endTour = false;

        $rootScope.startTour = function(forceStart){
            if(!$rootScope.endTour || forceStart)
            {
                var steps = TourService.getCurrentPageSetup();

                $rootScope.tour = new Tour({
                    steps: steps,
                    storage : false,
                    orphan : true,
                    onEnd: function(){
                        $rootScope.endTour = true;
                    }
                });
                // Initialize the tour
                $rootScope.tour.init();

                $rootScope.tour.start(true);
            }
        };

        $rootScope.setTheme = function(theme){
            var url,color;
            if(theme === 'wooden')
            {
                url = 'url(app/images/wooden.jpg)';
            }
            else if(theme === 'capital')
            {
                url = 'url(app/images/capital.jpg)';
                color = 'mediumaquamarine';
            }
            else if(theme === 'risk')
            {
                url = 'url(app/images/risk.jpg)';
                color = 'indianred';
            }
            else if(theme === 'investment')
            {
                url = 'url(app/images/investment.jpg)';
                color = 'dodgerblue';
            }
            else if(theme === 'lumen')
            {
                url = 'url(app/images/lumen.jpg)';
                color = 'grey';
            }
            else if(theme === 'misys')
            {
                url = 'url(app/images/misys.jpg)';
                color = 'indigo';
            }
            else
            {
                url = 'url(app/images/banking.jpg)';
                color = 'sienna';
            }

            $('.appBody').css('background-color',color);
            $('.appBody').css('background-image',url);
            //$('.modal-content').css('background',url);
            $('.appBody').css('background-size','100% 100%');
            //$('.modal-content').css('background-size','100% 100%');
            $('.appBody').css('background-attachment','fixed');

        };


        var setLogoutMessage = function ($timeout) {
            message = {
                text: "You have been successfully logged out.",
                type: "success",
                show: true
            };
        };

        $rootScope.$on(AUTH_EVENTS.logoutSuccess, setLogoutMessage);

        $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
            if($rootScope.tour && $rootScope.tour._state && !$rootScope.tour._state.end)
            {

                $rootScope.tour.end();
                $rootScope.endTour = false;
            }

            // Sync the session with the server side and show login modal dialog if required
            AuthService.syncSession().then(function () {
                // If user is not logged in and the route needs login show the modal dialog
                if (!AuthService.isAuthenticated() && angular.isDefined(currRoute.needsLogin) && currRoute.needsLogin) {
                $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
              }
            });
            $rootScope.animateTransition = false;
           
            // Animate transitions if needed
            if (angular.isDefined(currRoute.needsTransition) && currRoute.needsTransition) {
              $rootScope.animateTransition = true;
            }

            // Clear messages after some time
            $timeout(function(){message = {};}, 5000);  

        });
    });
}());