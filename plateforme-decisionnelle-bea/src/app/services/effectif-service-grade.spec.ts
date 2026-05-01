import { TestBed } from '@angular/core/testing';

import { EffectifServiceGrade } from './effectif-service-grade';

describe('EffectifServiceGrade', () => {
  let service: EffectifServiceGrade;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifServiceGrade);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
