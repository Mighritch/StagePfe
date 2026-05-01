import { TestBed } from '@angular/core/testing';

import { EffectifParGrade } from './effectif-par-grade';

describe('EffectifParGrade', () => {
  let service: EffectifParGrade;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifParGrade);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
