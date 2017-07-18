items = {
        'add_company': {
            'label': 'Add Company',
            'action': function(obj) {
							showDialogAddCompany(false);
            }
        },

        'edit_company': {
            'label': 'Edit Company',
            'separator_before': true,
            'action': function(obj) {
							showDialogEditCompany();
            }
        },

        'remove_company': {
            'label': 'Remove Company',
            'separator_before': true,
            'action': function(obj) {
							removeCompany();
            }
        },

        'expand_collapse_tree': {
			'label': 'Expand/Collapse',
			'separator_before': true,
			'action': function(obj) {
							expandCollapseTree();
			}
        }
};