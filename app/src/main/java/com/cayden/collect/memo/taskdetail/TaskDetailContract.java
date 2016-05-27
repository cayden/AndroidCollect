package com.cayden.collect.memo.taskdetail;

import com.cayden.collect.memo.BasePresenter;
import com.cayden.collect.memo.BaseView;

/**
 * Created by cuiran on 16/5/27.
 */
public interface TaskDetailContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}
