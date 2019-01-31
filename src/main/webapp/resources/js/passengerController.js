function PassengerService($resource) {
    return $resource('rest/passenger/:login/:TypeReq?srcAddr=:srcAddr&dstAddr=:dstAddr',
        { login: '@login',TypeReq: '@TypeReq',dstAddr:'@dstAddr', srcAddr:'@srcAddr' });
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function PassengerCtrl($scope, $http, PassengerService, InfoShareService){
    $scope.needNew = false;
    $scope.login = InfoShareService.getUser();
    PassengerService.query({login:$scope.login, TypeReq:"orders"}, function (value){$scope.orders = value;});
    $scope.newOrder = function(){
        if (isEmpty($scope.srcAddr)){
            alert("Enter source address, please!");
        } else if(isEmpty($scope.dstAddr)){
            alert("Enter destination address, please!");
        } else{
            alert("We add your order!");
            PassengerService.query({login:$scope.login, TypeReq:"new_order",srcAddr:$scope.srcAddr,
                    dstAddr:$scope.dstAddr}, function (value){$scope.orders = value;});
            PassengerService.query({login:$scope.login, TypeReq:"orders"}, function (value){$scope.orders = value;});
            $scope.srcAddr="";
            $scope.dstAddr="";
            /*InfoShareService.setUser($scope.login);
            window.location.href = '#/' + $scope.userType;*/

        }

    };
    $scope.showNew = function(){
        $scope.needNew = !$scope.needNew;
    };
}


app
    .factory('PassengerService', PassengerService)
    .controller('PassengerCtrl', PassengerCtrl);