define([
    'backbone',
    'tmpl/admin'
], function(
    Backbone,
    tmpl
) {

    return Backbone.View.extend({

        template: tmpl,
        el: 'div#admin',

        initialize: function() {

        },

        render: function() {
            this.$el.html(adminTmpl());
            $.ajax({
                type: "GET",
                url: "/adminpage",
                data: null,
                success: function(data) {
                    var data = JSON.stringify(data);
                    data = JSON.parse(data);

                    try {
                        var allUsers = new Array();
                        allUsers = data['users'];
                        if (String(allUsers[1]).length == 1) {
                            $("#usersTable").append("<tr><td>" + allUsers + "</td></tr>")
                        } else {
                            for (i = 0; i < allUsers.length; i++) {
                                $("#usersTable").append("<tr><td>" + allUsers[i] + "</td></tr>")
                            }
                        }
                    } catch (error) {
                        $("#usersTable").append("<tr><td>Nobody</td></tr>")
                    }

                    try {
                        var sessions = new Array();
                        sessions = data['sessions'];
                        if (String(sessions[1]).length == 1) {
                            $("#loggedTable").append("<tr><td>" + String(sessions) + "</td></tr>")
                        } else {
                            for (i = 0; i < sessions.length; i++) {
                                $("#loggedTable").append("<tr><td>" + sessions[i] + "</td></tr>")
                            }
                        }
                    } catch (error) {
                        $("#loggedTable").append("<tr><td>Nobody</td></tr>")
                    }


                },
                dataType: "json",
            });
        },

        show: function() {
            this.$el.show();
        },

        hide: function() {
            this.$el.hide();
        }

    });

});
