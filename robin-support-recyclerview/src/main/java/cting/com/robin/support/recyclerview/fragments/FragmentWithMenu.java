package cting.com.robin.support.recyclerview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cting.com.robin.support.recyclerview.R;

public class FragmentWithMenu extends Fragment{

    protected static final int ITEM_ID_EXPORT = 1;
    protected static final int ITEM_ID_IMPORT = 2;
    protected static final int ITEM_ID_ADD = 3;
    protected static final int ITEM_ID_SAVE = 4;
    protected static final int ITEM_ID_REVERT = 5;
    protected static final int ITEM_ID_EDIT = 6;

    public FragmentWithMenu() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        addSaveMenu(menu);
    }

    protected MenuItem addExportMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_EXPORT, 0, R.string.menu_title_export)
                .setIcon(android.R.drawable.stat_sys_upload_done);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return item;
    }

    protected MenuItem addImportMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_IMPORT, 0, R.string.menu_title_import)
                .setIcon(android.R.drawable.stat_sys_download_done);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return item;
    }

    protected MenuItem addAddMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_IMPORT, 0, R.string.menu_title_add)
                .setIcon(android.R.drawable.ic_menu_add);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return item;
    }

    protected MenuItem addSaveMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_SAVE, 0, R.string.menu_title_save)
                .setIcon(android.R.drawable.ic_menu_save);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return item;
    }

    protected MenuItem addRevertMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_REVERT, 0, R.string.menu_title_revert)
                .setIcon(android.R.drawable.ic_menu_revert);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return item;
    }

    protected MenuItem addEditMenu(Menu menu) {
        MenuItem item = menu.add(0, ITEM_ID_EDIT, 0, R.string.menu_title_edit)
                .setIcon(android.R.drawable.ic_menu_edit);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return item;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ITEM_ID_EXPORT) {
            selectMenuExport();
        }else if (id == ITEM_ID_IMPORT) {
            selectMenuImport();
        } else if (id == ITEM_ID_ADD) {
            selectMenuAdd();
        }else if (id == ITEM_ID_SAVE) {
            selectMenuSave();
        } else if (id == ITEM_ID_REVERT) {
            selectMenuRevert();
        } else if (id == ITEM_ID_EDIT) {
            selectMenuEdit();
        }
        return true;
    }

    protected void selectMenuExport() {
    }

    protected void selectMenuImport() {
    }

    protected void selectMenuAdd() {
    }

    protected void selectMenuSave(){
    }

    protected void selectMenuRevert() {
    }

    protected void selectMenuEdit() {
    }
}
