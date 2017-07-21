$(document).ready(function() {

	js_tree              = $('#jsTree');
	js_tree_is_expanded  = true;
	js_tree_root_node_id = '#';

	$.ajax({
			type: 'GET',
			url: '/mng-org-struct/api/jstree_data',
			dataType: 'json',

			success: function (data) {
			    initJsTree( data['data'] );
			},

			error: function (e) {
				alert('error');
			}
	});


	$('#inputSearchCompany').keyup(function(){
		var str_search = $(this).val();
		js_tree.jstree('search', str_search);
	});


    $('#dialogAddCompany').dialog({
			autoOpen: false,
			modal: true,
			title: 'Add Company',
			width: 400,
			buttons: [
						{
							text: 'Ok',
							click: function() {
								addCompany();
								//$(this).dialog('close');
							}
						},
						{
							text: 'Cancel',
							click: function() {
								$(this).dialog('close');
							}
						}
			]
    });

    $('#dialogEditCompany').dialog({
			autoOpen: false,
			modal: true,
			title: 'Edit Company',
			width: 400,
			buttons: [
						{
							text: 'Ok',
							click: function() {
								editCompany();
								//$(this).dialog('close');
							}
						},
						{
							text: 'Cancel',
							click: function() {
								$(this).dialog('close');
							}
						}
			]
    });

    $('#dialogIncorrectCompanyData').dialog({
			autoOpen: false,
			modal: true,
			title: 'Incorrect Company Data',
			width: 400,
			buttons: [
						{
							text: 'Ok',
							click: function() {
								$(this).dialog('close');
							}
						}
			]
    });

	$('#dialogConfirmRemoveCompany').dialog({
			autoOpen: false,
			modal: true,
			title: 'Delete Company With Children Companies',
			height: 'auto',
			width: 400,
			buttons: [
						{
							text: 'Delete',
							click: function() {
									var node_id = $('#dialogConfirmRemoveCompany').data('node_id');
									removeCompanyHelper(node_id);
									$(this).dialog( 'close' );
							}
						},
						{
							text: 'Cancel',
							click: function() {
									$(this).dialog( 'close' );
							}
						}
			]
    });

	$('#btnAddMainCompany').click(function(){
										showDialogAddCompany(true);
	});

	$('#btnExpandCollapseTree').click(function(){
										expandCollapseTree();
	});

});


/*****************************************************************************************************
 *										INIT_JS_TREE
 *****************************************************************************************************/
 function initJsTree(data) {

    js_tree.jstree({
					'core': {
						'check_callback': true,

						'themes': {
							'icons': false,
							'dots': true,
							'responsive': true,
							'stripes': true
						},

						'data': data,
					},

					'plugins': ['contextmenu', 'search'],

					'contextmenu': {
						'items': items
					},

					'search': {
						'case_insensitive': true,
						'show_only_matches': true
					}

    }).bind('loaded.jstree', function (event, data) {
								$(this).jstree('open_all');
	}).bind('hover_node.jstree',function(e,data){
      							$('#' + data.node.id).prop('title', 'Name | Earnings | Total_Earnings');
    }).bind("dblclick.jstree", function (event) {
      							var node_id = $(this).jstree('get_selected')[0];
      							if( $(this).jstree('is_leaf', node_id) ) {
      								showDialogEditCompany();
      							}
    });
}


/*****************************************************************************************************
 *										ADD_COMPANY
 *****************************************************************************************************/
function addCompany() {
	var name_company        = $.trim( $('#dialogAddCompany #nameCompany').val() );
	var earnings_company    = $('#dialogAddCompany #earningsCompany').val();
	var is_add_main_company = $('#dialogAddCompany #isMainCompany').prop('checked');

	if ( !isCorrectCompanyData(name_company, earnings_company) ) {
		return;
	}

	var selected_node_id    = $('#dialogAddCompany').data('selected_node_id');

	var parent_node_id = is_add_main_company ? js_tree_root_node_id : selected_node_id;

	var data = {
		'parent_id': parent_node_id,
		'name': name_company,
		'earnings': earnings_company
	}

	$.ajax({
			type: 'POST',
			url: '/mng-org-struct/api/add_company',
			contentType: 'application/json',
			data: JSON.stringify(data),
			dataType: 'json',

			success: function (data) {
				var child_node_id = data['child_id'];
				var text =  name_company + ' | ' + earnings_company;

				js_tree.jstree( 'create_node',
								parent_node_id,
								{
									'id': child_node_id,
									'text': text
								},
								'last',
								function() {
									updateTotalEarnings(child_node_id, earnings_company);
								}
				);

			},

			error: function (e) {
				alert('error');
			}
	});

	$('#dialogAddCompany').dialog('close');
}


/*****************************************************************************************************
 *										EDIT_COMPANY
 *****************************************************************************************************/
function editCompany() {
	var cur_node_id            = $('#dialogEditCompany').data('selected_node_id');
	var cur_name_company       = $('#dialogEditCompany').data('cur_name_company');
	var cur_earnings_company   = $('#dialogEditCompany').data('cur_earnings_company');
	var total_earnings_company = $('#dialogEditCompany').data('total_earnings_company');

	var new_name_company     = $.trim($('#dialogEditCompany #nameCompanyE').val());
	var new_earnings_company = $('#dialogEditCompany #earningsCompanyE').val();

	if ( !isCorrectCompanyData(new_name_company, new_earnings_company) ) {
		return;
	}

	var is_name_changed = cur_name_company.localeCompare(new_name_company) != 0;
	var is_earnings_changed = cur_earnings_company != new_earnings_company;

	if ( is_name_changed || is_earnings_changed ) {
		var data = {
			'id': cur_node_id,
			'name': new_name_company,
			'earnings': new_earnings_company,
			'is_name_changed': is_name_changed,
			'is_earnings_changed': is_earnings_changed
		}

		$.ajax({
				type: 'POST',
				url: '/mng-org-struct/api/edit_company',
				contentType: 'application/json',
				data: JSON.stringify(data),
				dataType: 'json',

				success: function (data) {
					var delta_earnings = new_earnings_company - cur_earnings_company;

					var new_text = is_name_changed ? new_name_company : cur_name_company;
					new_text += ' | ';
					new_text += is_earnings_changed ? new_earnings_company : cur_earnings_company;

					if ( total_earnings_company != 0 ) {
						new_text += ' | ';
						new_text += (total_earnings_company + delta_earnings);
					}

					var cur_node = js_tree.jstree('get_node', cur_node_id);
					js_tree.jstree('rename_node', cur_node, new_text);

					if ( is_earnings_changed ) {
						updateTotalEarnings(cur_node_id, delta_earnings);
					}
				},

				error: function (e) {
					alert('error');
				}
		});
	}

	$('#dialogEditCompany').dialog('close');
}

/*****************************************************************************************************
 *										REMOVE_COMPANY
 *****************************************************************************************************/
function removeCompany() {
	var node_id = js_tree.jstree('get_selected')[0];

	if ( nodeHasChildren(node_id) ) {
		$('#dialogConfirmRemoveCompany').data('node_id', node_id);
		$('#dialogConfirmRemoveCompany').dialog('open');
		return;
	}

	removeCompanyHelper(node_id);
}


/*****************************************************************************************************
 *								REMOVE_COMPANY_HELPER
 *****************************************************************************************************/
function removeCompanyHelper(node_id) {
	var data = { 'id': node_id };

	$.ajax({
			type: 'POST',
			url: '/mng-org-struct/api/remove_company',
			contentType: 'application/json',
			data: JSON.stringify(data),
			dataType: 'json',

			success: function (data) {
				var array = js_tree.jstree('get_node', node_id)['text'].split('|');
				var total_earnings_company = (array[2] == undefined) ? array[1] : array[2];
				updateTotalEarnings(node_id, -1 * total_earnings_company);

				js_tree.jstree('delete_node', node_id);
			},

			error: function (e) {
				alert('error');
			}
	});
}


/*****************************************************************************************************
 *								NODE_HAS_CHILDREN
 *****************************************************************************************************/
function nodeHasChildren(node_id) {
	var arr_children_ids = js_tree.jstree('get_node', node_id)['children'];
	return (arr_children_ids.length != 0)
}


/*****************************************************************************************************
 *								IS_CORRECT_COMPANY_DATA
 *****************************************************************************************************/
function isCorrectCompanyData(name_company, earnings_company) {
	var is_name_company_correct = ( name_company.length != 0 && name_company.indexOf('|') == -1 ) ? true : false;
	var is_earnings_company_correct = ($.isNumeric(earnings_company) && parseInt(earnings_company) >= 0 ) ? true : false;

	if ( !is_name_company_correct ) {
		$('#txtIncorrectName').html('Enter correct name of company!');
	} else {
		$('#txtIncorrectName').html('');
	}

	if ( !is_earnings_company_correct ) {
		$('#txtIncorrectEarnings').html('Enter correct earnings of company!');
	} else {
		$('#txtIncorrectEarnings').html('');
	}

	var is_correct_company_data = is_name_company_correct && is_earnings_company_correct;
	if ( !is_correct_company_data ) { $('#dialogIncorrectCompanyData').dialog('open'); }

	return is_correct_company_data;
}


/*****************************************************************************************************
 *								UPDATE_TOTAL_EARNINGS
 *****************************************************************************************************/
function updateTotalEarnings(node_id, earnings) {
	var parent_node_ids = js_tree.jstree('get_node', node_id)['parents'];

	for ( var i = 0; i < parent_node_ids.length-1; i++ ) {
		var node = js_tree.jstree('get_node', parent_node_ids[i]);

		var text = node['text'];
		var array = text.split('|');

		var name_company = $.trim(array[0]);
		var earnings_company = array[1];
		var total_earnings_company = (array[2] == undefined) ? earnings_company : array[2];

		var new_total_earning_company = parseInt(total_earnings_company) + parseInt(earnings);

		var new_text = name_company + ' | ' + earnings_company;
		if ( new_total_earning_company != earnings_company ) {
			new_text = new_text + ' | ' + new_total_earning_company;
		}

		js_tree.jstree('rename_node', node, new_text);
	}
}


/*****************************************************************************************************
 *								SHOW_DIALOG_ADD_COMPANY
 *****************************************************************************************************/
function showDialogAddCompany(is_create_only_main_company) {
	var selected_node_id = is_create_only_main_company ? js_tree_root_node_id : js_tree.jstree('get_selected')[0];
	$('#dialogAddCompany').data('selected_node_id', selected_node_id);

	$('#dialogAddCompany #nameCompany').val('');
	$('#dialogAddCompany #earningsCompany').val('100');
	$('#dialogAddCompany #isMainCompany').prop('checked', is_create_only_main_company ? true : false);

	if ( is_create_only_main_company ) {
		$('#dialogAddCompany #isMainCompany').prop('disabled', 'disabled');
	} else {
		$('#dialogAddCompany #isMainCompany').removeAttr('disabled');
	}

	$('#dialogAddCompany').dialog('open');
}


/*****************************************************************************************************
 *								SHOW_DIALOG_EDIT_COMPANY
 *****************************************************************************************************/
function showDialogEditCompany() {
	var selected_node_id = js_tree.jstree('get_selected')[0];

	var array = js_tree.jstree('get_node', selected_node_id)['text'].split('|');
	var name_company           = $.trim(array[0]);
	var earnings_company       = parseInt(array[1]);
	var total_earnings_company = (array[2] == undefined ? 0 : parseInt(array[2]));

	$('#dialogEditCompany #nameCompanyE').val(name_company);
	$('#dialogEditCompany #earningsCompanyE').val(earnings_company);

	$('#dialogEditCompany').data('selected_node_id', selected_node_id);
	$('#dialogEditCompany').data('cur_name_company', name_company);
	$('#dialogEditCompany').data('cur_earnings_company', earnings_company);
	$('#dialogEditCompany').data('total_earnings_company', total_earnings_company);

	$('#dialogEditCompany').dialog('open');
}


/*****************************************************************************************************
 *								EXPAND_COLLAPSE_TREE
 *****************************************************************************************************/
function expandCollapseTree() {
	js_tree_is_expanded ? js_tree.jstree('close_all') : js_tree.jstree('open_all');
	js_tree_is_expanded = !js_tree_is_expanded;
}