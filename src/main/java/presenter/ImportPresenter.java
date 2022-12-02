package presenter;

import boundary.Import.ImportOutputBoundary;
import responseModel.Import.ImportResponseModel;

public class ImportPresenter implements ImportOutputBoundary {
    @Override
    public ImportResponseModel successfulImport(ImportResponseModel calendar){
        return calendar;
    }

    public ImportResponseModel failedImport(String error){
    }
    //td: add return statement and reflect the error on the view
}
