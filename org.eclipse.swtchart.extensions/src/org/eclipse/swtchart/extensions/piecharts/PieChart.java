/*******************************************************************************
 * Copyright (c) 2020 SWTChart project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Himanshu Balasamanta Orignal API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.piecharts;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ICircularSeries;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ChartSettings;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IPrimaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.exceptions.SeriesException;

public class PieChart extends ScrollableChart {

	public PieChart() {

		super();
	}

	public PieChart(Composite parent, int style) {

		super(parent, style);
	}

	public void addSeriesData(ICircularSeriesData model) {

		/*
		 * Suspend the update when adding new data to improve the performance.
		 */
		if(model != null && model.getRootNode() != null) {
			BaseChart baseChart = getBaseChart();
			baseChart.suspendUpdate(true);
			// for(IPieSeriesData pieSeriesData : pieSeriesDataList) {
			/*
			 * Get the series data and apply the settings.
			 */
			try {
				ICircularSeriesSettings pieSeriesSettings = (ICircularSeriesSettings)model.getSettings();
				IChartSettings chartSettings = getChartSettings();
				//
				chartSettings.setHorizontalSliderVisible(false);
				chartSettings.setVerticalSliderVisible(false);
				//
				chartSettings.getRangeRestriction().setZeroX(false);
				chartSettings.getRangeRestriction().setZeroY(false);
				chartSettings.setLegendVisible(true);
				//
				IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
				primaryAxisSettingsX.setVisible(false);
				IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
				primaryAxisSettingsY.setVisible(false);
				//chartSettings.setShowPositionMarker(true);
				chartSettings.setShowLegendMarker(true);
				applySettings(chartSettings);
				ICircularSeries pieSeries = (ICircularSeries)createCircularSeries(model, pieSeriesSettings);
				baseChart.applyCircularSeriesSettings(pieSeries, pieSeriesSettings);
			} catch(SeriesException e) {
				//
			}
			// }
			baseChart.suspendUpdate(false);
			adjustRange(true);
			baseChart.redraw();
		} else {
			// throw error
		}
	}
}
