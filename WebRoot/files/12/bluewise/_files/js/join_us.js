/**
 * Created by dongchangkun on 2015-05-08.
 */
$(function(){
    $(".job_content").on("show.bs.collapse", function(){
        $(this).parents("tr").prev().find("td:eq(0) .icon").addClass("job_content_expanded");
    });

    $(".job_content").on("hide.bs.collapse", function(){
        $(this).parents("tr").prev().find("td:eq(0) .icon").removeClass("job_content_expanded");
    });
});
