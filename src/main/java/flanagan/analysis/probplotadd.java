        // ALTRNATIVE GAUSSIAN PROBABILITY PLOT
        public void alt(){
            this.lastMethod = 0;

            // Check for suffient data points
            this.gaussianNumberOfParameters = 2;
            if(this.numberOfDataPoints<3)throw new IllegalArgumentException("There must be at least three data points - preferably considerably more");

            // Create an instance of GaussianMaxFunction
            GaussianMaxFunction gmf = new GaussianMaxFunction();
            gmf.sortedData = this.sortedData;
            gmf.nData = this.nData;

            // Create instance of Maximization
            Maximization max = new Maximization();

            // Initial values
            double start[] = {this.mean, this.standardDeviation};
            double step[] = {this.mean/5.0, this.standardDeviation/5.0};
            if(step[0]==0.0)step[0] = step[1];

            // Add constraint; sigma>0
            max.addConstraint(1, -1, 0);

            // Obtain best probability plot varying mu and sigma
            // by maximizing the correlation coefficient between the ordered data and the ordered statistic medians
            max.nelderMead(gmff, start, step);




            // Get mu and sigma for best correlation coefficient
            this.gaussianParam = min.getBestEstimates();

            // Get mu and sigma errors for best correlation coefficient
            this.gaussianParamErrors = min.getBestEstimatesErrors();

            // Calculate Gaussian order statistic medians
            this.gaussianOrderMedians = Stat.gaussianOrderStatisticMedians(this.gaussianParam[0], this.gaussianParam[1], this.numberOfDataPoints);

            // Regression of the ordered data on the Gaussian order statistic medians
            Regression reg = new Regression(this.gaussianOrderMedians, this.sortedData);
            reg.linear();

            // Intercept and gradient of best fit straight line
            this.gaussianLine = reg.getBestEstimates();

            // Estimated erors of the intercept and gradient of best fit straight line
            this.gaussianLineErrors = reg.getBestEstimatesErrors();

            // Correlation coefficient
            this.gaussianCorrCoeff = reg.getSampleR();

            // Initialize data arrays for plotting
            double[][] data = PlotGraph.data(2,this.numberOfDataPoints);

            // Assign data to plotting arrays
            data[0] = this.gaussianOrderMedians;
            data[1] = this.sortedData;

            data[2] = this.gaussianOrderMedians;
            for(int i=0; i<this.numberOfDataPoints; i++){
                data[3][i] = this.gaussianLine[0] + this.gaussianLine[1]*this.gaussianOrderMedians[i];
            }

            // Get sum of squares
            this.gaussianSumOfSquares = min.getSumOfSquares();

            // Create instance of PlotGraph
            PlotGraph pg = new PlotGraph(data);
            int[] points = {4, 0};
            pg.setPoint(points);
            int[] lines = {0, 3};
            pg.setLine(lines);
            pg.setXaxisLegend("Gaussian Order Statistic Medians");
            pg.setYaxisLegend("Ordered Data Values");
            pg.setGraphTitle("Gaussian probability plot:   gradient = " + Fmath.truncate(this.gaussianLine[1], 4) + ", intercept = "  +  Fmath.truncate(this.gaussianLine[0], 4) + ",  R = " + Fmath.truncate(this.gaussianCorrCoeff, 4));
            pg.setGraphTitle2("  mu = " + Fmath.truncate(this.gaussianParam[0], 4) + ", sigma = "  +  Fmath.truncate(this.gaussianParam[1], 4));

            // Plot
            pg.plot();

            this.gaussianDone = true;
        }

        public void normalProbabilityPlot(){
            this.gaussianProbabilityPlot();
        }

        // Return Gaussian mu
        public double gaussianMu(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParam[0];
        }

        // Return Gaussian mu error
        public double gaussianMuError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParamErrors[0];
        }

        // Return Gaussian sigma
        public double gaussianSigma(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParam[1];
        }

        // Return Gaussian sigma error
        public double gaussianSigmaError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParamErrors[1];
        }

        // Return the Gaussian gradient
        public double gaussianGradient(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLine[1];
        }

        // Return the error of the Gaussian gradient
        public double gaussianGradientError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLineErrors[1];
        }

        // Return the Gaussian intercept
        public double gaussianIntercept(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLine[0];
        }

        // Return the error of the Gaussian intercept
        public double gaussianInterceptError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLineErrors[0];
        }

        // Return the Gaussian correlation coefficient
        public double gaussianCorrelationCoefficient(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianCorrCoeff;
        }

        // Return the sum of squares at the Gaussian minimum
        public double gaussianSumOfSquares(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianSumOfSquares;
        }

        // Return the unweighted sum of squares at the Gaussian minimum
        public double gaussianUnweightedSumOfSquares(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianUnweightedSumOfSquares;
        }


        // Return Gaussian order statistic medians
        public double[] gaussianOrderStatisticMedians(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianOrderMedians;
        }


        public double normalMu(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParam[0];
        }

        // Return Gaussian mu error
        public double normalMuError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParamErrors[0];
        }

        // Return Gaussian sigma
        public double normalSigma(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParam[1];
        }

        // Return Gaussian sigma error
        public double normalSigmaError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianParamErrors[1];
        }

        // Return the Gaussian gradient
        public double normalGradient(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLine[1];
        }

        // Return the error of the Gaussian gradient
        public double normalGradientError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLineErrors[1];
        }

        // Return the Gaussian intercept
        public double normalIntercept(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLine[0];
        }

        // Return the error of the Gaussian intercept
        public double normalInterceptError(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianLineErrors[0];
        }

        // Return the Gaussian correlation coefficient
        public double normalCorrelationCoefficient(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianCorrCoeff;
        }

        // Return the sum of squares at the Gaussian minimum
        public double normalSumOfSquares(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianSumOfSquares;
        }

        // Return Gaussian order statistic medians
        public double[] normalOrderStatisticMedians(){
            if(!this.gaussianDone)throw new IllegalArgumentException("Gaussian Probability Plot method has not been called");
            return this.gaussianOrderMedians;
        }



        // STANDARD GAUSSIAN PROBABILITY PLOT
        public void gaussianStandardProbabilityPlot(){
            this.lastMethod = 14;

            // Check for suffient data points
            this.gaussianStandardNumberOfParameters = 2;
            if(this.numberOfDataPoints<3)throw new IllegalArgumentException("There must be at least three data points - preferably considerably more");

            // Calculate Standard Gaussian order statistic medians
            this.gaussianStandardOrderMedians = Stat.gaussianOrderStatisticMedians(this.numberOfDataPoints);

            // Regression of the ordered data on the Standard Gaussian order statistic medians
            Regression reg = new Regression(this.gaussianStandardOrderMedians, this.sortedData);
            reg.linear();

            // Intercept and gradient of best fit straight line
            this.gaussianStandardLine = reg.getBestEstimates();

            // Estimated erors of the intercept and gradient of best fit straight line
            this.gaussianStandardLineErrors = reg.getBestEstimatesErrors();

            // Correlation coefficient
            this.gaussianStandardCorrCoeff = reg.getSampleR();

            // Initialize data arrays for plotting
            double[][] data = PlotGraph.data(2,this.numberOfDataPoints);

            // Assign data to plotting arrays
            data[0] = this.gaussianStandardOrderMedians;
            data[1] = this.sortedData;

            data[2] = this.gaussianStandardOrderMedians;
            for(int i=0; i<this.numberOfDataPoints; i++){
                data[3][i] = this.gaussianStandardLine[0] + this.gaussianStandardLine[1]*this.gaussianStandardOrderMedians[i];
            }

            // Create instance of PlotGraph
            PlotGraph pg = new PlotGraph(data);
            int[] points = {4, 0};
            pg.setPoint(points);
            int[] lines = {0, 3};
            pg.setLine(lines);
            pg.setXaxisLegend("Standard Gaussian Order Statistic Medians");
            pg.setYaxisLegend("Ordered Data Values");
            pg.setGraphTitle("Standard Gaussian probability plot:   gradient = " + Fmath.truncate(this.gaussianStandardLine[1], 4) + ", intercept = "  +  Fmath.truncate(this.gaussianStandardLine[0], 4) + ",  R = " + Fmath.truncate(this.gaussianStandardCorrCoeff, 4));

            // Plot
            pg.plot();

            this.gaussianStandardDone = true;
        }

        public void normalStandardProbabilityPlot(){
            this.gaussianStandardProbabilityPlot();
        }

        // Return the Standard Gaussian gradient
        public double gaussianStandardGradient(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLine[1];
        }

        // Return the error of the Standard Gaussian gradient
        public double gaussianStandardGradientError(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLineErrors[1];
        }

        // Return the Standard Gaussian intercept
        public double gaussianStandardIntercept(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLine[0];
        }

        // Return the error of the Standard Gaussian intercept
        public double gaussianStandardInterceptError(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLineErrors[0];
        }

        // Return the Standard Gaussian correlation coefficient
        public double gaussianStandardCorrelationCoefficient(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardCorrCoeff;
        }

        // Return the sum of squares at the Standard Gaussian minimum
        public double gaussianStandardSumOfSquares(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardSumOfSquares;
        }

        // Return Standard Gaussian order statistic medians
        public double[] gaussianStandardOrderStatisticMedians(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardOrderMedians;
        }


        // Return the Standard Gaussian gradient
        public double normalStandardGradient(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLine[1];
        }

        // Return the error of the Standard Gaussian gradient
        public double normalstandardGradientError(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLineErrors[1];
        }

        // Return the Standard Gaussian intercept
        public double normalStandardInterceptError(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardLineErrors[0];
        }

        // Return the Standard Gaussian correlation coefficient
        public double normalStandardCorrelationCoefficient(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardCorrCoeff;
        }

        // Return the sum of squares at the Standard Gaussian minimum
        public double normalStandardSumOfSquares(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardSumOfSquares;
        }

        // Return Standard Gaussian order statistic medians
        public double[] normalStandardOrderStatisticMedians(){
            if(!this.gaussianStandardDone)throw new IllegalArgumentException("Standard Gaussian Probability Plot method has not been called");
            return this.gaussianStandardOrderMedians;
        }

        // LOGISTIC PROBABILITY PLOT
        public void logisticProbabilityPlot(){
            this.lastMethod = 8;

            // Check for suffient data points
            this.logisticNumberOfParameters = 2;
            if(this.numberOfDataPoints<3)throw new IllegalArgumentException("There must be at least three data points - preferably considerably more");

            // Create instance of Regression
            Regression min = new Regression(this.sortedData, this.sortedData);
            double muest = mean;
            if(muest==0.0)muest = this.standardDeviation/3.0;
            double betaest = this.standardDeviation;
            double[] start = {muest, betaest};
            this.initialEstimates = start;
            double[] step = {0.3*muest, 0.3*betaest};
            double tolerance = 1e-10;

             // Add constraint; beta>0
            min.addConstraint(1, -1, 0);

            // Create an instance of LogisticProbPlotFunc
            LogisticProbPlotFunc lppf = new LogisticProbPlotFunc();
            lppf.setDataArray(this.numberOfDataPoints);

            // Obtain best probability plot varying mu and sigma
            // by minimizing the sum of squares of the differences between the ordered data and the ordered statistic medians
            min.simplex(lppf, start, step, tolerance);

            // Get mu and beta for best correlation coefficient
            this.logisticParam = min.getBestEstimates();

            // Get mu and beta errors for best correlation coefficient
            this.logisticParamErrors = min.getBestEstimatesErrors();

            // Get sum of squares
            this.logisticSumOfSquares = min.getSumOfSquares();

            // Calculate Logistic order statistic medians
            this.logisticOrderMedians = Stat.logisticOrderStatisticMedians(this.logisticParam[0], this.logisticParam[1], this.numberOfDataPoints);

            // Regression of the ordered data on the Logistic order statistic medians
            Regression reg = new Regression(this.logisticOrderMedians, this.sortedData);
            reg.linear();

            // Intercept and gradient of best fit straight line
            this.logisticLine = reg.getBestEstimates();

            // Estimated erors of the intercept and gradient of best fit straight line
            this.logisticLineErrors = reg.getBestEstimatesErrors();

            // Correlation coefficient
            this.logisticCorrCoeff = reg.getSampleR();

            // Initialize data arrays for plotting
            double[][] data = PlotGraph.data(2,this.numberOfDataPoints);

            // Assign data to plotting arrays
            data[0] = this.logisticOrderMedians;
            data[1] = this.sortedData;

            data[2] = logisticOrderMedians;
            for(int i=0; i<this.numberOfDataPoints; i++){
                data[3][i] = this.logisticLine[0] + this.logisticLine[1]*logisticOrderMedians[i];
            }

            // Create instance of PlotGraph
            PlotGraph pg = new PlotGraph(data);
            int[] points = {4, 0};
            pg.setPoint(points);
            int[] lines = {0, 3};
            pg.setLine(lines);
            pg.setXaxisLegend("Logistic Order Statistic Medians");
            pg.setYaxisLegend("Ordered Data Values");
            pg.setGraphTitle("Logistic probability plot:   gradient = " + Fmath.truncate(this.logisticLine[1], 4) + ", intercept = "  +  Fmath.truncate(this.logisticLine[0], 4) + ",  R = " + Fmath.truncate(this.logisticCorrCoeff, 4));
            pg.setGraphTitle2("  mu = " + Fmath.truncate(this.logisticParam[0], 4) + ", beta = "  +  Fmath.truncate(this.logisticParam[1], 4));

            // Plot
            pg.plot();

            this.logisticDone = true;
            this.probPlotDone = true;
        }

        // Return Logistic mu
        public double logisticMu(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticParam[0];
        }

        // Return Logistic mu error
        public double logisticMuError(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticParamErrors[0];
        }

        // Return Logistic beta
        public double logisticBeta(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticParam[1];
        }

        // Return Logistic beta error
        public double logisticBetaError(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticParamErrors[1];
        }

        // Return Logistic order statistic medians
        public double[] logisticOrderStatisticMedians(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticOrderMedians;
        }

        // Return the Logistic gradient
        public double logisticGradient(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticLine[1];
        }

        // Return the error of the Logistic gradient
        public double logisticGradientError(){
            if(!this.logisticDone)throw new IllegalArgumentException("Logistic Probability Plot method has not been called");
            return this.logisticLineErrors[1];
        }

        // Return the Logistic intercept
        public double l