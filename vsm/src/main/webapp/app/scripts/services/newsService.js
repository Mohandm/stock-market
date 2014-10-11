var vsmApp = angular.module('vsmApp');

vsmApp.service('NewsService', ['$http', function ($http) {

    this.newsUrl = 'http://articlefeeds.nasdaq.com/nasdaq/symbols?symbol=';

    this.reloadNews = function(ticker){
        if(ticker === null)
        {
            ticker = 'AAPL';
        }
        /* RSS Feeds widget */
        $('#news-feed').FeedEk({
            FeedUrl:this.newsUrl+ticker,
            MaxCount: 15,
            ShowDesc: false,
            ShowPubDate: true,
            DescCharacterLimit: 0
        });
    };

}]);