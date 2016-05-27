package com.cayden.collect.memo.statistics;

import com.cayden.collect.memo.BasePresenter;
import com.cayden.collect.memo.BaseView;

/**
 * Created by cuiran on 16/5/27.
 */
public interface StatisticsContract {
    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}
