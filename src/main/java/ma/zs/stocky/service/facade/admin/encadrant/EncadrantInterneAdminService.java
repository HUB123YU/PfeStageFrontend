package ma.zs.stocky.service.facade.admin.encadrant;

import java.util.List;
import ma.zs.stocky.bean.core.encadrant.EncadrantInterne;
import ma.zs.stocky.dao.criteria.core.encadrant.EncadrantInterneCriteria;
import ma.zs.stocky.zynerator.service.IService;


import org.springframework.web.multipart.MultipartFile;

public interface EncadrantInterneAdminService {



    List<EncadrantInterne> findByGenreId(Long id);
    int deleteByGenreId(Long id);
    long countByGenreCode(String code);
    List<EncadrantInterne> findByNationalitId(Long id);
    int deleteByNationalitId(Long id);
    long countByNationalitCode(String code);




	EncadrantInterne create(EncadrantInterne t);

    EncadrantInterne update(EncadrantInterne t);

    List<EncadrantInterne> update(List<EncadrantInterne> ts,boolean createIfNotExist);

    EncadrantInterne findById(Long id);

    EncadrantInterne findOrSave(EncadrantInterne t);

    EncadrantInterne findByReferenceEntity(EncadrantInterne t);

    EncadrantInterne findWithAssociatedLists(Long id);

    List<EncadrantInterne> findAllOptimized();

    List<EncadrantInterne> findAll();

    List<EncadrantInterne> findByCriteria(EncadrantInterneCriteria criteria);

    List<EncadrantInterne> findPaginatedByCriteria(EncadrantInterneCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(EncadrantInterneCriteria criteria);

    List<EncadrantInterne> delete(List<EncadrantInterne> ts);

    void deleteByIdIn(List<Long> ids);

    boolean deleteById(Long id);

    List<List<EncadrantInterne>> getToBeSavedAndToBeDeleted(List<EncadrantInterne> oldList, List<EncadrantInterne> newList);

    List<EncadrantInterne> importData(List<EncadrantInterne> items);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    List<EncadrantInterne> importExcel(MultipartFile file);

    String generateUsername(String firstName, String lastName);
}
