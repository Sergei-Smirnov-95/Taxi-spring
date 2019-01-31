function OperatorService($resource) {
    return $resource('rest/operator/:login/:reqType?OrID=:OrID&DrID=:DrID',
        {reqType: '@reqType',  login: '@login', OrID:'@OrID', DrID:'@DrID' });
}

function OperatorCtrl($scope, $http, OperatorService, InfoShareService){
    $scope.login = InfoShareService.getUser();
    OperatorService.query({login:$scope.login, reqType: "orders"}, function (value){$scope.orders = value;});
    $scope.drivers = OperatorService.query({login:$scope.login, reqType: "drivers"});

    $scope.select = function(value){
        alert("Order selected!");
        $scope.OrID= value;
    };
    $scope.selectDr = function(value){
        alert("Driver selected!");
        $scope.DrID= value;
    };
    $scope.update = function(){
        alert("update");
        OperatorService.query({login:$scope.login, reqType: "orders"},
            function (value){$scope.orders = value;});
        $scope.drivers = OperatorService.query({login:$scope.login, reqType: "drivers"});
    };
    $scope.appoint = function () {
        alert("appoint");
        if (!$scope.OrID){
            alert("choose order!");
        } else if (!$scope.DrID){
            alert("choose driver!");
        } else {

            $scope.OK = OperatorService.query({login:$scope.login,
                reqType: "appoint", OrID:$scope.OrID, DrID:$scope.DrID});
            if($scope.OK) {
                $scope.update();
            }
        }
    };
}


app
    .factory('OperatorService', OperatorService)
    .controller('OperatorCtrl', OperatorCtrl);