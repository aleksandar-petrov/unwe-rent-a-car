package bg.unwe.aleksandarpetrov.rentacar.web.controller;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.ExportType;
import bg.unwe.aleksandarpetrov.rentacar.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exports")
public class ExportController {

  private final ExportService exportService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public String generate(@RequestParam ExportType exportType) {
    return exportService.export(exportType);
  }
}
