import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PracticaTestModule } from '../../../test.module';
import { MedicamentosDetailComponent } from 'app/entities/medicamentos/medicamentos-detail.component';
import { Medicamentos } from 'app/shared/model/medicamentos.model';

describe('Component Tests', () => {
  describe('Medicamentos Management Detail Component', () => {
    let comp: MedicamentosDetailComponent;
    let fixture: ComponentFixture<MedicamentosDetailComponent>;
    const route = ({ data: of({ medicamentos: new Medicamentos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [MedicamentosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicamentosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicamentosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medicamentos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicamentos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
