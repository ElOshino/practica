import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PracticaTestModule } from '../../../test.module';
import { SignosVitalesDetailComponent } from 'app/entities/signos-vitales/signos-vitales-detail.component';
import { SignosVitales } from 'app/shared/model/signos-vitales.model';

describe('Component Tests', () => {
  describe('SignosVitales Management Detail Component', () => {
    let comp: SignosVitalesDetailComponent;
    let fixture: ComponentFixture<SignosVitalesDetailComponent>;
    const route = ({ data: of({ signosVitales: new SignosVitales(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [SignosVitalesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SignosVitalesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SignosVitalesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load signosVitales on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.signosVitales).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
