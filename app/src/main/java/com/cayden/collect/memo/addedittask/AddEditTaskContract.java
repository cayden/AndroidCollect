package com.cayden.collect.memo.addedittask;

import com.cayden.collect.memo.BasePresenter;
import com.cayden.collect.memo.BaseView;

/**
 * Created by cuiran on 16/5/27.
 */
public interface AddEditTaskContract {
    interface View extends BaseView<Presenter>{
        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void createTask(String title, String description);

        void updateTask( String title, String description);

        void populateTask();
    }
}
