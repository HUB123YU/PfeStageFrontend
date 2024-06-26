package  ma.zs.stocky.ws.facade.admin.appartenance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zs.stocky.bean.core.appartenance.Genre;
import ma.zs.stocky.dao.criteria.core.appartenance.GenreCriteria;
import ma.zs.stocky.service.facade.admin.appartenance.GenreAdminService;
import ma.zs.stocky.ws.converter.appartenance.GenreConverter;
import ma.zs.stocky.ws.dto.appartenance.GenreDto;
import ma.zs.stocky.zynerator.controller.AbstractController;
import ma.zs.stocky.zynerator.dto.AuditEntityDto;
import ma.zs.stocky.zynerator.util.PaginatedList;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zs.stocky.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zs.stocky.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/genre/")
public class GenreRestAdmin {




    @Operation(summary = "Finds a list of all genres")
    @GetMapping("")
    public ResponseEntity<List<GenreDto>> findAll() throws Exception {
        ResponseEntity<List<GenreDto>> res = null;
        List<Genre> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<GenreDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all genres")
    @GetMapping("optimized")
    public ResponseEntity<List<GenreDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<GenreDto>> res = null;
        List<Genre> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<GenreDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a genre by id")
    @GetMapping("id/{id}")
    public ResponseEntity<GenreDto> findById(@PathVariable Long id) {
        Genre t = service.findById(id);
        if (t != null) {
            GenreDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a genre by code")
    @GetMapping("code/{code}")
    public ResponseEntity<GenreDto> findByCode(@PathVariable String code) {
	    Genre t = service.findByReferenceEntity(new Genre(code));
        if (t != null) {
            GenreDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  genre")
    @PostMapping("")
    public ResponseEntity<GenreDto> save(@RequestBody GenreDto dto) throws Exception {
        if(dto!=null){
            Genre myT = converter.toItem(dto);
            Genre t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                GenreDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  genre")
    @PutMapping("")
    public ResponseEntity<GenreDto> update(@RequestBody GenreDto dto) throws Exception {
        ResponseEntity<GenreDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Genre t = service.findById(dto.getId());
            converter.copy(dto,t);
            Genre updated = service.update(t);
            GenreDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of genre")
    @PostMapping("multiple")
    public ResponseEntity<List<GenreDto>> delete(@RequestBody List<GenreDto> dtos) throws Exception {
        ResponseEntity<List<GenreDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<Genre> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }
    @Operation(summary = "Delete the specified genre")
    @DeleteMapping("")
    public ResponseEntity<GenreDto> delete(@RequestBody GenreDto dto) throws Exception {
		ResponseEntity<GenreDto> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dto != null) {
            Genre t = converter.toItem(dto);
            service.delete(Arrays.asList(t));
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dto, status);
        return res;
    }

    @Operation(summary = "Delete the specified genre")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }
    @Operation(summary = "Delete multiple genres by ids")
    @DeleteMapping("multiple/id")
    public ResponseEntity<List<Long>> deleteByIdIn(@RequestBody List<Long> ids) throws Exception {
        ResponseEntity<List<Long>> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (ids != null) {
            service.deleteByIdIn(ids);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(ids, status);
        return res;
     }



    @Operation(summary = "Finds a genre and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<GenreDto> findWithAssociatedLists(@PathVariable Long id) {
        Genre loaded =  service.findWithAssociatedLists(id);
        GenreDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds genres by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<GenreDto>> findByCriteria(@RequestBody GenreCriteria criteria) throws Exception {
        ResponseEntity<List<GenreDto>> res = null;
        List<Genre> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<GenreDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated genres by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody GenreCriteria criteria) throws Exception {
        List<Genre> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<GenreDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets genre data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody GenreCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<GenreDto> findDtos(List<Genre> list){
        List<GenreDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<GenreDto> getDtoResponseEntity(GenreDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    @Autowired private GenreAdminService service;
    @Autowired private GenreConverter converter;





}
