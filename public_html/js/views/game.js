define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    return Backbone.View.extend({

        template: tmpl,
        el: 'div#game',
        initialize: function () {
            // TODO
        },
        render: function () {
            this.$el.html(gameTmpl());

            $(".game-cell").click(function() {
                if ($(this).attr('class') == 'game-cell')
                    $(this).addClass('hover');
                else
                    $(this).removeClass('hover');
            });
        },
        show: function () {
            this.$el.show();

            $.ajax({
                    type: "POST",
                    url: "/game",
                    data: null,
                    success: function(){
                       alert("ZAPROS OTPRAVIL I POLUCHIL OTVET 200 OK")
                    },
            });

            var socket = new WebSocket("ws://localhost:1488/game");
              // отправить сообщение из формы publish

              // обработчик входящих сообщений
              socket.onopen = function(e){
                alert("СОЕДИНЕНИЕ УСТАНОВЛЕНО");
                 // socket.send("1 4 2");
              };

              socket.onmessage = function(event) {
                var incomingMessage = event.data;
                alert(incomingMessage);
              };

        },
        hide: function () {
            this.$el.hide();
        },
        init: function() {

        },

    });

});
