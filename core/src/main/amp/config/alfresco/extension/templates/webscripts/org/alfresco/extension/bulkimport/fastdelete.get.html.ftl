[#ftl]
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<html>
  <head>
    <meta charset="utf-8">
    <link href='//fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600' rel='stylesheet' type='text/css'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Bulk Import Tool</title>
    <meta name="description" content="UI Web Script for the Bulk Import Tool">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    [#-- favicons - good lord!  o.O --]
    <link rel="shortcut icon" href="${url.context}/images/bulkimport/favicon.ico" type="image/x-icon" />
    <link rel="apple-touch-icon" href="${url.context}/images/bulkimport/apple-touch-icon.png" />
    <link rel="apple-touch-icon" sizes="57x57" href="${url.context}/images/bulkimport/apple-touch-icon-57x57.png" />
    <link rel="apple-touch-icon" sizes="72x72" href="${url.context}/images/bulkimport/apple-touch-icon-72x72.png" />
    <link rel="apple-touch-icon" sizes="76x76" href="${url.context}/images/bulkimport/apple-touch-icon-76x76.png" />
    <link rel="apple-touch-icon" sizes="114x114" href="${url.context}/images/bulkimport/apple-touch-icon-114x114.png" />
    <link rel="apple-touch-icon" sizes="120x120" href="${url.context}/images/bulkimport/apple-touch-icon-120x120.png" />
    <link rel="apple-touch-icon" sizes="144x144" href="${url.context}/images/bulkimport/apple-touch-icon-144x144.png" />
    <link rel="apple-touch-icon" sizes="152x152" href="${url.context}/images/bulkimport/apple-touch-icon-152x152.png" />
    [#-- JQuery --]
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.9.1.js"></script>
    <script src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    [#-- Bulk import --]
    <script src="${url.context}/scripts/bulkimport/vendor/modernizr-2.6.2.min.js"></script>
    <link rel="stylesheet" href="${url.context}/css/bulkimport/normalize.css">
    <link rel="stylesheet" href="${url.context}/css/bulkimport/main.css">
    <link rel="stylesheet" href="${url.context}/css/bulkimport/bulkimport.css">
  </head>
  <body>
    <!--[if lt IE 7]>
        <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <div class="container">
      <div class="block">
        <img style="margin:15px;vertical-align:middle" src="${url.context}/images/bulkimport/apple-touch-icon-57x57.png" alt="Alfresco Bulk Import Tool" />
      </div>
      <div class="block">
        <h1><strong>Bulk Import Tool v2.0-SNAPSHOT</strong></h1>
      </div>
    </div>

    <form id="initiateDeleteForm" action="${url.service}" method="post" charset="utf-8">
      <fieldset><legend>Deletion Target</legend>
        <p><label for="targetPath">Space to delete:</label> <input type="text" id="targetPath" name="targetPath" size="80" required/></p>
      </fieldset>

      <p><strong style="color:red">Warning! This operation will delete the space and <u>all</u> contents unconditionally!  It cannot be interrupted or undone!</strong></p>
      <p><button class="button green" type="submit" name="submit">Delete!</button></p>
      <p>Note: this operation can take a very long time (minutes to hours), and does not provide any status reporting.</p>
    </form>
    <p>Please see the <a target="_blank" href="https://github.com/pmonks/alfresco-bulk-import">project site</a> for documentation, known issues, updated versions, etc.</p>
    <hr>
    <p class="footnote">Alfresco ${server.edition} v${server.version}</p>
    <script>
      [#-- Target field autocomplete --]
      $(function() {
        $('#targetPath').autocomplete({
          source: '${url.context}/s/bulk/import/ajax/suggest/spaces.json',
          minLength: 2
        });
      });

      [#-- Disable submit button on form submission --]
      $("#initiateDeleteForm").submit(function(event) {
        var submitButton = $(this).find("button[type='submit']");

        submitButton.prop('disabled', true);
        submitButton.switchClass('green', 'gray');
      });
    </script>
  </body>
</html>
