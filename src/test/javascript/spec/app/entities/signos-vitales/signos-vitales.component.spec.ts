import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PracticaTestModule } from '../../../test.module';
import { SignosVitalesComponent } from 'app/entities/signos-vitales/signos-vitales.component';
import { SignosVitalesService } from 'app/entities/signos-vitales/signos-vitales.service';
import { SignosVitales } from 'app/shared/model/signos-vitales.model';

describe('Component Tests', () => {
  describe('SignosVitales Management Component', () => {
    let comp: SignosVitalesComponent;
    let fixture: ComponentFixture<SignosVitalesComponent>;
    let service: SignosVitalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [SignosVitalesComponent]
      })
        .overrideTemplate(SignosVitalesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SignosVitalesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SignosVitalesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SignosVitales(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.signosVitales && comp.signosVitales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
