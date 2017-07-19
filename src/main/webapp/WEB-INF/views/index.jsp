<html>
<head>
    <title>Companies</title>

    <link rel='stylesheet' href='resources/css/jstree/style.min.css'>
    <link rel='stylesheet' href='resources/css/ui/jquery-ui.css'>

    <link rel='stylesheet' href='resources/css/style.css'>

    <script src='resources/js/external/jquery-3.2.1.min.js'></script>
    <script src='resources/js/external/jquery-ui.js'></script>
    <script src='resources/js/external/jstree.min.js'></script>

    <script src='resources/js/contextmenu-items.js'></script>
    <script src='resources/js/main.js'></script>
</head>
<body>
	<center>
		<h1 class='title'>MANAGE ORGANIZATIONAL STRUCTURE</h1>
	</center>

    <div>
        <button id='btnAddMainCompany' class='ui-button ui-corner-all ui-widget'>Add Main Company</button>
        <button id='btnExpandCollapseTree' class='ui-button ui-corner-all ui-widget'>Expand/Collapse</button>
    </div>

    <hr>

    <div id='jsTree'></div>

    <div id='dialogAddCompany'>
        <table>
            <tr>
                <td>Main Company:</td>
                <td><input type='checkbox' id='isMainCompany' title='Checked if this company is main.'/></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input type='text' id='nameCompany' placeholder='input name company' /></td>
            </tr>
            <tr>
                <td>Earnings:</td>
                <td><input type='number' id='earningsCompany' value='100' min='0' max='40000000' step='100' /></td>
            </tr>
        </table>
    </div>

    <div id='dialogEditCompany'>
        <table>
            <tr>
                <td>Name:</td>
                <td><input type='text' id='nameCompanyE' /></td>
            </tr>
            <tr>
                <td>Earnings:</td>
                <td><input type='number' id='earningsCompanyE' min='0' max='40000000' step='100' /></td>
            </tr>
        </table>
    </div>

    <div id='dialogConfirmRemoveCompany'>
        <p>
            <span class='ui-icon ui-icon-alert' style='float:left; margin:12px 12px 20px 0;'></span>
            This company will be deleted with children companies. Are you sure?
        </p>
    </div>
</body>
</html>