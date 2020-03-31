import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PracticaTestModule } from '../../../test.module';
import { SignosVitalesUpdateComponent } from 'app/entities/signos-vitales/signos-vitales-update.component';
import { SignosVitalesService } from 'app/entities/signos-vitales/signos-vitales.service';
import { SignosVitales } from 'app/shared/model/signos-vitales.model';

describe('Component Tests', () => {
  describe('SignosVitales Management Update Component', () => {
    let comp: SignosVitalesUpdateComponent;
    let fixture: ComponentFixture<SignosVitalesUpdateComponent>;
    let service: SignosVitalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [SignosVitalesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SignosVitalesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SignosVitalesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SignosVitalesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SignosVitales(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SignosVitales();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
