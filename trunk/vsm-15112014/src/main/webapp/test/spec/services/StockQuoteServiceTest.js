describe('Unit: StockQuoteService', function() {
    // Load the module with MainController
    beforeEach(module('vsmApp'));

    var stockQuoteService;
    beforeEach(inject(function (_StockQuotesService_) {
        stockQuoteService = _StockQuotesService_;
    }));

   it('should return reduced historical data', function() {
        var historicalData = [0,1,2,3,4,5,6,7,8,9];

        var reducedhistoricalData = stockQuoteService.reducedHistoricalData(historicalData, 2);

        expect(reducedhistoricalData.length).toBe(2);
    });

    it('should return beginning subset of historical data', function() {
        var historicalData = [0,1,2,3,4,5,6,7,8,9];

        var reducedhistoricalData = stockQuoteService.reducedHistoricalData(historicalData, 2);

        expect(reducedhistoricalData).toEqual([8,9]);
    });
})