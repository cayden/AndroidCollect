package com.cayden.collect.memo.taskdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.google.common.base.Preconditions.checkNotNull;
import com.cayden.collect.memo.data.Task;
import com.cayden.collect.memo.data.source.TasksDataSource;
import com.cayden.collect.memo.data.source.TasksRepository;

/**
 * Created by cuiran on 16/5/27.
 */
public class TaskDetailPresenter implements TaskDetailContract.Presenter {

        private final TasksRepository mTasksRepository;

        private final TaskDetailContract.View mTaskDetailView;

        @Nullable
        private String mTaskId;

        public TaskDetailPresenter(@Nullable String taskId,
                @NonNull TasksRepository tasksRepository,
                @NonNull TaskDetailContract.View taskDetailView) {
            this.mTaskId = taskId;
            mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
            mTaskDetailView = checkNotNull(taskDetailView, "taskDetailView cannot be null!");

            mTaskDetailView.setPresenter(this);
        }

        @Override
        public void start() {
            openTask();
        }

    private void openTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.setLoadingIndicator(true);
        mTasksRepository.getTask(mTaskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                // The view may not be able to handle UI updates anymore
                if (!mTaskDetailView.isActive()) {
                    return;
                }
                mTaskDetailView.setLoadingIndicator(false);
                if (null == task) {
                    mTaskDetailView.showMissingTask();
                } else {
                    showTask(task);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mTaskDetailView.isActive()) {
                    return;
                }
                mTaskDetailView.showMissingTask();
            }
        });
    }

    @Override
    public void editTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTaskDetailView.showEditTask(mTaskId);
    }

    @Override
    public void deleteTask() {
        mTasksRepository.deleteTask(mTaskId);
        mTaskDetailView.showTaskDeleted();
    }

    @Override
    public void completeTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.completeTask(mTaskId);
        mTaskDetailView.showTaskMarkedComplete();
    }

    @Override
    public void activateTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTasksRepository.activateTask(mTaskId);
        mTaskDetailView.showTaskMarkedActive();
    }

    private void showTask(Task task) {
        String title = task.getTitle();
        String description = task.getDescription();

        if (title != null && title.isEmpty()) {
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (description != null && description.isEmpty()) {
            mTaskDetailView.hideDescription();
        } else {
            mTaskDetailView.showDescription(description);
        }
        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }
}
